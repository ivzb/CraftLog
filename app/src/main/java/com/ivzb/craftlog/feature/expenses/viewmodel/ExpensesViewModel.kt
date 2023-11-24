package com.ivzb.craftlog.feature.expenses.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.feature.home.usecase.GetExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase
) : ViewModel() {

    var state by mutableStateOf(ExpensesState())
        private set

    init {
        loadExpenses()
    }

    fun loadExpenses() {
        viewModelScope.launch {
            getExpensesUseCase.getExpenses().onEach { expensesList ->
                state = state.copy(
                    expenses = expensesList
                )
            }.launchIn(viewModelScope)
        }
    }
}
