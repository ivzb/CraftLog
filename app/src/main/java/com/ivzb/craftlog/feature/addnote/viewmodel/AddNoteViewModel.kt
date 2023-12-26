package com.ivzb.craftlog.feature.addnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.addnote.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
) : ViewModel() {

    private val _isNoteSaved = MutableSharedFlow<Unit>()
    val isNoteSaved = _isNoteSaved.asSharedFlow()

    fun createNote(
        content: String,
        tags: List<String>,
        date: Date
    ): Note {
        return Note(
            id = 0,
            content = content,
            tags = tags,
            date = date,
            // todo: try to fetch url and create link
            link = null,
        )
    }

    fun addNote(state: AddNoteState) {
        viewModelScope.launch {
            val note = state.note
            val noteAdded = addNoteUseCase.addNote(note)
            _isNoteSaved.emit(noteAdded)
        }
    }

}
