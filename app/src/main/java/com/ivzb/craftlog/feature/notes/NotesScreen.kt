package com.ivzb.craftlog.feature.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ivzb.craftlog.R
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.extenstion.toRelativeDateString
import com.ivzb.craftlog.feature.expenses.ExpenseListItem
import com.ivzb.craftlog.feature.notes.NoteListItem.HeaderItem
import com.ivzb.craftlog.feature.notes.NoteListItem.NoteItem
import com.ivzb.craftlog.feature.notes.NoteListItem.OverviewItem
import com.ivzb.craftlog.feature.notes.viewmodel.NotesViewModel
import com.ivzb.craftlog.navigation.navigateToEditNote
import com.ivzb.craftlog.navigation.navigateToNoteDetail
import com.ivzb.craftlog.ui.components.ListHeader
import com.ivzb.craftlog.ui.components.ExpandableSearchView
import com.ivzb.craftlog.util.trim

@Composable
fun NotesRoute(
    navController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadNotes()
    }

    NotesScreen(navController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavHostController,
    viewModel: NotesViewModel,
) {
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
                onNoteDetail = { note ->
                    navController.navigateToNoteDetail(note)
                },
                onEdit = { note ->
                    navController.navigateToEditNote(note)
                },
                onDelete = { note ->
                    viewModel.deleteNote(note)
                }
            )
        }
    }
}

@Composable
fun NoteList(
    notes: List<Note>,
    searchQuery: String = "",
    onNoteDetail: (Note) -> Unit,
    onEdit: (Note) -> Unit,
    onDelete: (Note) -> Unit
) {
    val sortedNoteList = notes
        .sortedByDescending { it.date }
        .map { NoteItem(it) }
        .groupBy { it.note.date.trim() }
        .flatMap { (time, notes) -> listOf(HeaderItem(time)) + notes }

    if (sortedNoteList.isEmpty()) {
        EmptyView()
    } else {
        NoteLazyColumn(sortedNoteList, searchQuery, onNoteDetail, onEdit, onDelete)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteLazyColumn(
    items: List<NoteListItem>,
    searchQuery: String,
    onNoteDetail: (Note) -> Unit,
    onEdit: (Note) -> Unit,
    onDelete: (Note) -> Unit
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
                NoteListItem(it, onNoteDetail, onEdit, onDelete)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.NoteListItem(
    it: NoteListItem,
    onNoteDetail: (Note) -> Unit,
    onEditNote: (Note) -> Unit,
    onDeleteNote: (Note) -> Unit
) {
    when (it) {
        is OverviewItem -> {}

        is HeaderItem -> ListHeader(it.time.toRelativeDateString())

        is NoteItem -> {
            NoteCard(
                modifier = Modifier.animateItemPlacement(),
                note = it.note,
                onNoteDetail,
                onEditNote,
                onDeleteNote
            )
        }
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
    ) : NoteListItem(-1)

    data class NoteItem(val note: Note, val offset: Long = 0L) :
        NoteListItem((note.id ?: 0) + offset)

    data class HeaderItem(val time: Long) : NoteListItem(time)
}