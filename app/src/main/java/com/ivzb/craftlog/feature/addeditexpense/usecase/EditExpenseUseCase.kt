package com.ivzb.craftlog.feature.addeditexpense.usecase

import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.repository.ExpenseRepository
import javax.inject.Inject

class EditExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {

    suspend fun editExpense(expense: Expense) {
        return repository.updateExpense(expense)
    }
}
