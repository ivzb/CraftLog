package com.ivzb.craftlog.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ivzb.craftlog.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(expenseEntity: NoteEntity): Long

    @Delete
    suspend fun deleteNote(expenseEntity: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(expenseEntity: NoteEntity)

    @Query(
        """
            SELECT *
            FROM noteentity
        """
    )
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query(
        """
            SELECT *
            FROM noteentity
            WHERE date > :date
        """
    )
    fun getNotesForDate(date: Date): Flow<List<NoteEntity>>

    @Query(
        """
            SELECT *
            FROM noteentity
            WHERE date >= :dateStart and date <= :dateEnd
        """
    )
    fun getNotesForDateRange(dateStart: Date, dateEnd: Date): Flow<List<NoteEntity>>

    @Query(
        """
            SELECT *
            FROM noteentity
            WHERE content LIKE :content || '%'
            ORDER BY id DESC
            LIMIT 3
        """
    )
    fun findNotes(content: String): Flow<List<NoteEntity>>
}
