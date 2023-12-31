package com.ivzb.craftlog.feature.notes.usecase

import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend fun deleteNote(note: Note) {
        return repository.deleteNote(note)
    }
}
