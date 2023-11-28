package com.ivzb.craftlog.feature.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.home.usecase.GetExpensesUseCase
import com.ivzb.craftlog.feature.home.usecase.UpdateExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        loadExpenses()
    }

    fun getUserName() {
        state = state.copy(userName = "Ivan")
        // TODO: Get user name from DB
    }

    fun getGreeting() {
        state = state.copy(greeting = "Greeting")
        // TODO: Get greeting by checking system time
    }

    fun loadExpenses() {
        viewModelScope.launch {
            getExpensesUseCase.getExpenses().onEach { expensesList ->
                state = state.copy(
                    expenses = expensesList,
                    loading = false,
                )
            }.launchIn(viewModelScope)
        }
    }

    fun takeExpense(expense: Expense) {
        viewModelScope.launch {
            updateExpenseUseCase.updateExpense(expense)
        }
    }
}
