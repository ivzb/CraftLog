package com.ivzb.craftlog.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ivzb.craftlog.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expenseEntity: ExpenseEntity): Long

    @Delete
    suspend fun deleteExpense(expenseEntity: ExpenseEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExpense(expenseEntity: ExpenseEntity)

    @Query(
        """
            SELECT *
            FROM expenseentity
        """
    )
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query(
        """
            SELECT *
            FROM expenseentity
            WHERE date > :date
        """
    )
    fun getExpensesForDate(date: Date): Flow<List<ExpenseEntity>>

    @Query(
        """
            SELECT *
            FROM expenseentity
            WHERE date >= :dateStart and date <= :dateEnd
        """
    )
    fun getExpensesForDateRange(dateStart: Date, dateEnd: Date): Flow<List<ExpenseEntity>>
}
