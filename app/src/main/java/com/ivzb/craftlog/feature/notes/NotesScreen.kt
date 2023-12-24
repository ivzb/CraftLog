package com.ivzb.craftlog.feature.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivzb.craftlog.R
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.notes.viewmodel.NotesViewModel
import com.ivzb.craftlog.ui.components.ExpandableSearchView

@Composable
fun NotesRoute(
    navigateToNoteDetail: (Note) -> Unit,
    viewModel: NotesViewModel = hiltViewModel()
) {

    NotesScreen(viewModel, navigateToNoteDetail)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(viewModel: NotesViewModel, navigateToNoteDetail: (Note) -> Unit) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(top = 16.dp),
                title = {
                    ExpandableSearchView(
                        searchText = searchQuery,
                        placeholderText = stringResource(id = R.string.note_placeholder),
                        titleText = stringResource(id = R.string.notes),
                        onSearch = {
                            if (searchQuery != it) {
                                searchQuery = it
                                viewModel.loadNotes(searchQuery)
                            }
                        },
                    )
                }
            )
        },
        bottomBar = { },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NoteList(
                notes = viewModel.state.notes,
                searchQuery = searchQuery,
                navigateToNoteDetail = navigateToNoteDetail
            )
        }
    }
}

@Composable
fun NoteList(
    notes: List<Note>,
    searchQuery: String = "",
    navigateToNoteDetail: (Note) -> Unit
) {
    val sortedNoteList = notes
        .sortedByDescending { it.date }
        .map { NoteListItem.NoteItem(it) }

    if (sortedNoteList.isEmpty()) {
        EmptyView()
    } else {
        NoteLazyColumn(sortedNoteList, searchQuery, navigateToNoteDetail)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteLazyColumn(
    items: List<NoteListItem>,
    searchQuery: String,
    navigateToNoteDetail: (Note) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(items.firstOrNull()) {
        if (items.isNotEmpty() && searchQuery.isEmpty()) {
            lazyListState.animateScrollToItem(0)
        }
    }

    LazyColumn(
        modifier = Modifier,
        lazyListState,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = items,
            key = { it.id },
            itemContent = {
                when (it) {
                    is NoteListItem.OverviewItem -> { }
                    is NoteListItem.HeaderItem -> {
                        Text(
                            modifier = Modifier
                                .animateItemPlacement()
                                .padding(4.dp, 12.dp, 8.dp, 0.dp)
                                .fillMaxWidth(),
                            text = it.headerText.uppercase(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    is NoteListItem.NoteItem -> {
                        NoteCard(
                            modifier = Modifier.animateItemPlacement(),
                            note = it.note,
                            navigateToNoteDetail = { note ->
                                navigateToNoteDetail(note)
                            }
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium,
            text = stringResource(id = R.string.no_notes_yet),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

sealed class NoteListItem(val id: Long) {

    data class OverviewItem(
        val notesToday: List<Note>,
        val isNoteListEmpty: Boolean
    ) : NoteListItem(-2)

    data class NoteItem(val note: Note) : NoteListItem(note.id ?: 0)

    data class HeaderItem(val headerText: String) : NoteListItem(-1)
}
