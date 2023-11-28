package com.ivzb.craftlog.feature.home.viewmodel

import com.ivzb.craftlog.domain.model.Expense

data class HomeState(
    val greeting: String = "",
    val userName: String = "",
    val expenses: List<Expense> = emptyList(),
    val loading: Boolean = true,
)
