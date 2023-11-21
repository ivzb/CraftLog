package com.waseefakhtar.doseapp.feature.history.viewmodel

import com.ivzb.craftlog.domain.model.Expense

data class ExpensesState(
    val expenses: List<Expense> = emptyList()
)
