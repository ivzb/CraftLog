package com.ivzb.craftlog.domain.repository

import com.ivzb.craftlog.domain.model.Investment
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface InvestmentRepository {

    suspend fun insertInvestment(investment: Investment)

    suspend fun deleteInvestment(investment: Investment)

    suspend fun updateInvestment(investment: Investment)

    fun getAllInvestments(): Flow<List<Investment>>

    fun getInvestmentsForDate(date: Date): Flow<List<Investment>>

    fun getInvestmentsForRange(year: Int, month: Int): Flow<List<Investment>>

    fun findInvestments(name: String): Flow<List<Investment>>

}
