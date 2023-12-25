package com.ivzb.craftlog.feature.addnote.usecase

import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.domain.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend fun addNote(note: Note) {
        repository.insertNote(note)
    }
}
