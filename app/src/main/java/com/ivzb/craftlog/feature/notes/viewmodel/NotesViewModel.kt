package com.ivzb.craftlog.feature.notes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.notes.usecase.DeleteNoteUseCase
import com.ivzb.craftlog.feature.notes.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {

    var state by mutableStateOf(NotesState())
        private set

    fun loadNotes(filter: String = "") {
        viewModelScope.launch {
            getNotesUseCase.getNotes().onEach { notesList ->
                val filteredNotesList = if (filter.isNotEmpty()) {
                    notesList.filter {
                        it.content.contains(filter, ignoreCase = true) || it.tags.any { it.contains(filter, ignoreCase = true) }
                    }
                } else {
                    notesList
                }

                state = state.copy(
                    notes = filteredNotesList
                )
            }.launchIn(viewModelScope)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase.deleteNote(note.copy())
        }
    }
}
