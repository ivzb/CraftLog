package com.ivzb.craftlog.feature.budget.usecase

import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import javax.inject.Inject

class GetBudgetUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {

    suspend fun getBudget(): Flow<List<Budget>> {
        return repository.getAllExpenses().map {
            listOf(Budget(2023, 11, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO))
        }
    }
}
