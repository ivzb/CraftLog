package com.ivzb.craftlog.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ivzb.craftlog.data.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budgetEntity: BudgetEntity): Long

    @Delete
    suspend fun deleteBudget(budgetEntity: BudgetEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBudget(budgetEntity: BudgetEntity)

    @Query(
        """
            SELECT *
            FROM budgetentity
            WHERE year = :year AND month = :month
        """
    )
    fun getBudgetForDate(year: Int, month: Int): Flow<BudgetEntity?>
}
