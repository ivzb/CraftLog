package com.ivzb.craftlog.feature.expenses.viewmodel

import com.ivzb.craftlog.domain.model.Expense

data class ExpensesState(
    val expenses: List<Expense> = emptyList()
)
