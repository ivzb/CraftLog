package com.ivzb.craftlog.feature.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.feature.expenses.usecase.GetExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

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

}
