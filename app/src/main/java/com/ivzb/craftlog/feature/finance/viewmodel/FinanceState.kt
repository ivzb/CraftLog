package com.ivzb.craftlog.feature.finance.viewmodel

import com.ivzb.craftlog.domain.model.BudgetOverview
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.model.Investment

data class FinanceState(
    val budgetOverview: BudgetOverview? = null,
    val expenses: List<Expense> = emptyList(),
    val investments: List<Investment> = emptyList(),
    val loading: Boolean = true,
)
