package com.ivzb.craftlog.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ivzb.craftlog.data.entity.InvestmentEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface InvestmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvestment(investmentEntity: InvestmentEntity): Long

    @Delete
    suspend fun deleteInvestment(investmentEntity: InvestmentEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInvestment(investmentEntity: InvestmentEntity)

    @Query(
        """
            SELECT *
            FROM investmententity
        """
    )
    fun getAllInvestments(): Flow<List<InvestmentEntity>>

    @Query(
        """
            SELECT *
            FROM investmententity
            WHERE date > :date
        """
    )
    fun getInvestmentsForDate(date: Date): Flow<List<InvestmentEntity>>

    @Query(
        """
            SELECT *
            FROM investmententity
            WHERE date >= :dateStart and date <= :dateEnd
        """
    )
    fun getInvestmentsForDateRange(dateStart: Date, dateEnd: Date): Flow<List<InvestmentEntity>>
}
