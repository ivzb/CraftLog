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

    suspend fun getBudget(year: Int, month: Int): Flow<Budget> {
        return repository.getAllExpenses().map {
            // todo: compute budget out of filtered expenses
            Budget(year, month, 1234.56.toBigDecimal(), 567.89.toBigDecimal(), 723.56.toBigDecimal())
        }
    }
}
