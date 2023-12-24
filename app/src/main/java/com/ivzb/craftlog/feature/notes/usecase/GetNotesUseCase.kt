package com.ivzb.craftlog.feature.notes.usecase

import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend fun getNotes(): Flow<List<Note>> {
        return repository.getAllNotes()
    }
}
