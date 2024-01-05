package com.ivzb.craftlog.feature.addeditexpense.usecase

import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindExpensesUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    suspend fun findExpenses(name: String): Flow<List<Expense>> {
        return repository.findExpenses(name)
    }
}
