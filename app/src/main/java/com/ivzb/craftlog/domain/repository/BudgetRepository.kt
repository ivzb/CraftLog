package com.ivzb.craftlog.domain.repository

import com.ivzb.craftlog.domain.model.Budget
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface BudgetRepository {

    suspend fun insertBudget(budget: Budget)

    suspend fun deleteBudget(budget: Budget)

    suspend fun updateBudget(budget: Budget)

    fun getBudgetForDate(year: Int, month: Int): Flow<Budget>
}
