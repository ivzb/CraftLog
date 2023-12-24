package com.ivzb.craftlog.data.repository

import com.ivzb.craftlog.data.dao.NoteDao
import com.ivzb.craftlog.data.mapper.toNote
import com.ivzb.craftlog.data.mapper.toNoteEntity
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.domain.repository.NoteRepository
import com.ivzb.craftlog.util.getDateRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override suspend fun insertNote(note: Note) {
        val entity = note.toNoteEntity()
        dao.insertNote(entity)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toNoteEntity())
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note.toNoteEntity())
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes().map { entities ->
            entities.map { it.toNote() }
        }
    }

    override fun getNotesForDate(date: Date): Flow<List<Note>> {
        return dao.getNotesForDate(
            date = date
        ).map { entities ->
            entities.map { it.toNote() }
        }
    }

    override fun getNotesForRange(year: Int, month: Int): Flow<List<Note>> {
        val (startDate, endDate) = getDateRange(year, month)

        return dao.getNotesForDateRange(
            startDate,
            endDate
        ).map { entities ->
            entities.map { it.toNote() }
        }
    }
}
