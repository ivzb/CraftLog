package com.ivzb.craftlog.feature.expenses.usecase

import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.repository.ExpenseRepository
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {

    suspend fun deleteExpense(expense: Expense) {
        return repository.deleteExpense(expense)
    }
}
