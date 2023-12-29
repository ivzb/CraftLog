package com.ivzb.craftlog.feature.addnote

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.analytics.AnalyticsHelper
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.addnote.viewmodel.AddNoteState
import com.ivzb.craftlog.feature.addnote.viewmodel.AddNoteViewModel
import com.ivzb.craftlog.util.SnackbarUtil.showSnackbar
import com.ivzb.craftlog.util.getItem
import com.ivzb.craftlog.util.isClipboardEnabled
import java.util.Date

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AddNoteRoute(onBackClicked = {}, navigateToNotes = {})
}

@Composable
fun AddNoteRoute(
    onBackClicked: () -> Unit,
    navigateToNotes: () -> Unit,
    viewModel: AddNoteViewModel = hiltViewModel()
) {
    val analyticsHelper = AnalyticsHelper.getInstance(LocalContext.current)
    AddNoteScreen(onBackClicked, navigateToNotes, viewModel, analyticsHelper)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    onBackClicked: () -> Unit,
    navigateToNotes: () -> Unit,
    viewModel: AddNoteViewModel,
    analyticsHelper: AnalyticsHelper,
) {
    var content by rememberSaveable { mutableStateOf("") }
    var tags by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    LaunchedEffect(Unit) {
        viewModel
            .isNoteSaved
            .collect {
                navigateToNotes()
                analyticsHelper.logEvent(AnalyticsEvents.NOTE_SAVED)
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                navigationIcon = {
                    FloatingActionButton(
                        onClick = {
                            analyticsHelper.logEvent(AnalyticsEvents.ADD_EXPENSE_ON_BACK_CLICKED)
                            onBackClicked()
                        },
                        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(id = R.string.add_note),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(56.dp),
                onClick = {
                    validateNote(
                        content = content,
                        tags = tags.split(',').map { it.trim() }.filterNot { it.isEmpty() },
                        onInvalidate = {
                            val invalidatedValue = context.getString(it)

                            showSnackbar(
                                context.getString(
                                    R.string.value_is_empty,
                                    invalidatedValue
                                )
                            )

                            val event = String.format(
                                AnalyticsEvents.ADD_NOTE_VALUE_INVALIDATED,
                                invalidatedValue
                            )

                            analyticsHelper.logEvent(event)
                        },
                        onValidate = { note ->
                            viewModel.addNote(AddNoteState(note))
                            analyticsHelper.logEvent(AnalyticsEvents.ADD_NOTE_ON_SAVE_CLICKED)
                        },
                        viewModel = viewModel
                    )
                },
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = stringResource(id = R.string.done),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = stringResource(id = R.string.name),
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                value = content,
                onValueChange = { content = it },
                maxLines = 5,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                trailingIcon = {
                    if (isClipboardEnabled(clipboard)) {
                        IconButton(
                            onClick = {
                                content = clipboard.getItem()
                            }
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_paste),
                                contentDescription = "Paste"
                            )
                        }
                    }
                },
                placeholder = { Text(text = stringResource(R.string.note_placeholder)) },
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = stringResource(id = R.string.tags),
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = tags,
                onValueChange = { tags = it },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                placeholder = { Text(text = stringResource(R.string.tags_placeholder)) },
            )
        }
    }
}

private fun validateNote(
    content: String,
    tags: List<String>,
    onInvalidate: (Int) -> Unit,
    onValidate: (Note) -> Unit,
    viewModel: AddNoteViewModel
) {
    if (content.isEmpty()) {
        onInvalidate(R.string.content)
        return
    }

    val note = viewModel.createNote(
        content = content,
        tags = tags,
        date = Date(),
    )

    onValidate(note)
}
