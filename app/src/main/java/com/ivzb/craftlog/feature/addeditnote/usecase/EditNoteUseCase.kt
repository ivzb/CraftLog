package com.ivzb.craftlog.feature.addeditnote.usecase

import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.domain.repository.NoteRepository
import javax.inject.Inject

class EditNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend fun editNote(note: Note) {
        return repository.updateNote(note)
    }
}
