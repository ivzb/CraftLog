package com.ivzb.craftlog.feature.home.viewmodel

import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.domain.model.Note

data class HomeState(
    val greeting: String = "",
    val userName: String = "",
    val expenses: List<Expense> = emptyList(),
    val investments: List<Investment> = emptyList(),
    val notes: List<Note> = emptyList(),
    val loading: Boolean = true,
)
