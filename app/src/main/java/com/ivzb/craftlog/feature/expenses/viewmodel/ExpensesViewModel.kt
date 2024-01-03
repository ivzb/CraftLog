package com.ivzb.craftlog.feature.expenses.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.expenses.usecase.DeleteExpenseUseCase
import com.ivzb.craftlog.feature.expenses.usecase.GetExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
) : ViewModel() {

    var state by mutableStateOf(ExpensesState())
        private set

    fun loadExpenses(filter: String = "") {
        viewModelScope.launch {
            getExpensesUseCase.getExpenses().onEach { expensesList ->
                val filteredExpensesList = if (filter.isNotEmpty()) {
                    expensesList.filter {
                        it.name.contains(filter, ignoreCase = true) || it.category.name.contains(filter, ignoreCase = true)
                    }
                } else {
                    expensesList
                }

                state = state.copy(
                    expenses = filteredExpensesList
                )
            }.launchIn(viewModelScope)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            deleteExpenseUseCase.deleteExpense(expense.copy())
        }
    }
}
