package com.ivzb.craftlog.feature.notedetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.notes.usecase.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {

    fun updateNote(note: Note) {
        viewModelScope.launch {
            updateNoteUseCase.updateNote(note.copy())
        }
    }
}
