package com.ivzb.craftlog.domain.repository

import com.ivzb.craftlog.domain.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface NoteRepository {

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)

    fun getAllNotes(): Flow<List<Note>>

    fun getNotesForDate(date: Date): Flow<List<Note>>

    fun getNotesForRange(year: Int, month: Int): Flow<List<Note>>

}
