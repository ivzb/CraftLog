package com.ivzb.craftlog.feature.budget.viewmodel

import com.ivzb.craftlog.domain.model.Budget

data class BudgetState(
    val budget: List<Budget> = emptyList()
)
