package com.ivzb.craftlog.feature.notedetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.analytics.AnalyticsHelper
import com.ivzb.craftlog.domain.model.Link
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.extenstion.toFormattedDateString
import com.ivzb.craftlog.feature.notedetail.viewmodel.NoteDetailViewModel
import com.ivzb.craftlog.navigation.navigateBack
import com.ivzb.craftlog.util.appendLink
import com.ivzb.craftlog.util.onLinkClick

@Composable
fun NoteDetailRoute(
    navController: NavHostController,
    note: Note?,
) {
    note?.let {
        NoteDetailScreen(navController, note)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    navController: NavHostController,
    note: Note,
) {
    val context = LocalContext.current
    val analyticsHelper = AnalyticsHelper.getInstance(context)

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                navigationIcon = {
                    FloatingActionButton(
                        onClick = {
                            analyticsHelper.logEvent(AnalyticsEvents.NOTE_DETAIL_ON_BACK_CLICKED)
                            navController.navigateBack()
                        },
                        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(id = R.string.note),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            )
        },
        bottomBar = { }
    ) { innerPadding ->
        SelectionContainer {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = note.date.toFormattedDateString(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                if (note.tags.isNotEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = note.tags.joinToString(", "),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                if (note.link != null) {
                    NoteDetailLink(note.link)
                }

                if (note.link?.url != note.content) {
                    NoteDetailText(note)
                }
            }
        }
    }
}

@Composable
private fun NoteDetailText(note: Note) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp),
        text = note.content,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
private fun NoteDetailLink(link: Link) {
    val uriHandler = LocalUriHandler.current

    AsyncImage(
        modifier = Modifier.fillMaxWidth(),
        model = link.imageUrl,
        contentDescription = "Note image preview",
        contentScale = ContentScale.FillWidth,
    )

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp),
        text = link.title ?: "",
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyLarge,
        overflow = TextOverflow.Ellipsis
    )

    val annotatedLink = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xff64B5F6), fontWeight = FontWeight.Bold)) {
            val url = link.url
            appendLink(url, url)
        }
    }

    ClickableText(
        text = annotatedLink,
        onClick = { offset ->
            annotatedLink.onLinkClick(offset) { link ->
                try {
                    uriHandler.openUri(link)
                } catch (_: Exception) {
                    // ignore
                }
            }
        },
        style = MaterialTheme.typography.bodyMedium,
    )
}
