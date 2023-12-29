package com.ivzb.craftlog.feature.notes.usecase

import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend fun updateNote(note: Note) {
        return repository.updateNote(note)
    }
}
