package com.ivzb.craftlog.feature.addexpense.usecase

import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.repository.ExpenseRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    suspend fun addExpense(expense: Expense) {
        repository.insertExpense(expense)
    }
}
