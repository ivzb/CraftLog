package com.ivzb.craftlog.feature.addnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Link
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.addnote.usecase.AddNoteUseCase
import com.ivzb.craftlog.feature.addnote.usecase.FetchLinkUseCase
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
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val fetchLinkUseCase: FetchLinkUseCase
) : ViewModel() {

    private val _isNoteSaved = MutableSharedFlow<Unit>()
    val isNoteSaved = _isNoteSaved.asSharedFlow()

    fun createNote(
        content: String,
        tags: List<String>,
        date: Date,
        link: Link? = null
    ): Note = Note(
        id = 0,
        content = content,
        tags = tags,
        date = date,
        link = link,
    )

    fun createNote(note: Note, link: Link?): Note =
        createNote(note.content, note.tags, note.date, link)

    fun addNote(state: AddNoteState) {
        viewModelScope.launch {
            val link = withContext(Dispatchers.Default) {
                fetchLinkUseCase.fetchLinkMetaData(state.note.content.extractUrl())
            }
            val note = createNote(state.note, link)
            val noteAdded = addNoteUseCase.addNote(note)
            _isNoteSaved.emit(noteAdded)
        }
    }

}
