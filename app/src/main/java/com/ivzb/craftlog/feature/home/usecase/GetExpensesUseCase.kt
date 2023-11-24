package com.ivzb.craftlog.feature.home.usecase

import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {

    suspend fun getExpenses(): Flow<List<Expense>> {
        return repository.getAllExpenses()
    }
}
