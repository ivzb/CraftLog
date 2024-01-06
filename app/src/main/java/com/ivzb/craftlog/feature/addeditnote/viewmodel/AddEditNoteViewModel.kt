package com.ivzb.craftlog.feature.addeditnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Link
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.addeditnote.usecase.AddNoteUseCase
import com.ivzb.craftlog.feature.addeditnote.usecase.EditNoteUseCase
import com.ivzb.craftlog.feature.addeditnote.usecase.FetchLinkUseCase
import com.ivzb.craftlog.util.extractUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val fetchLinkUseCase: FetchLinkUseCase
) : ViewModel() {

    private val _isNoteSaved = MutableSharedFlow<Unit>()
    val isNoteSaved = _isNoteSaved.asSharedFlow()

    fun createNote(
        id: Long,
        content: String,
        tags: List<String>,
        date: Date,
        link: Link? = null,
        additionalData: Map<String, String>
    ): Note = Note(
        id = id,
        content = content,
        tags = tags,
        date = date,
        link = link,
        additionalData = additionalData
    )

    fun addNote(state: AddEditNoteState) {
        viewModelScope.launch {
            val link = withContext(Dispatchers.Default) {
                fetchLinkUseCase.fetchLinkMetaData(state.note.content.extractUrl())
            }
            val note = state.note.copy(link = link)
            val noteAdded = addNoteUseCase.addNote(note)
            _isNoteSaved.emit(noteAdded)
        }
    }

    fun editNote(state: AddEditNoteState) {
        viewModelScope.launch {
            val link = withContext(Dispatchers.Default) {
                fetchLinkUseCase.fetchLinkMetaData(state.note.content.extractUrl())
            }
            val note = state.note.copy(link = link)
            val noteEdited = editNoteUseCase.editNote(note)
            _isNoteSaved.emit(noteEdited)
        }
    }

}
