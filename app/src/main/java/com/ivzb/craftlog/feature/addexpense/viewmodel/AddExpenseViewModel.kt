package com.ivzb.craftlog.feature.addexpense.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.addexpense.usecase.AddExpenseUseCase
import com.ivzb.craftlog.feature.addexpense.usecase.FindExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val findExpensesUseCase: FindExpensesUseCase
) : ViewModel() {

    private val _isExpenseSaved = MutableSharedFlow<Unit>()
    val isExpenseSaved = _isExpenseSaved.asSharedFlow()

    private val _suggestedExpenses = MutableSharedFlow<List<Expense>>()
    val suggestedExpenses = _suggestedExpenses.asSharedFlow()

    fun createExpense(
        name: String,
        amount: BigDecimal,
        categoryId: String,
        date: Date,
        additionalData: Map<String, String>
    ): Expense {
        return Expense(
            id = 0,
            name = name,
            amount = amount,
            categoryId = categoryId,
            date = date,
            additionalData = additionalData
        )
    }

    fun addExpense(state: AddExpenseState) {
        viewModelScope.launch {
            val expense = state.expense
            val expenseAdded = addExpenseUseCase.addExpense(expense)
            _isExpenseSaved.emit(expenseAdded)
        }
    }

    fun suggestExpenses(name: String) {
        viewModelScope.launch {
            val suggestedExpenses = findExpensesUseCase.findExpenses(name)
            _suggestedExpenses.emitAll(suggestedExpenses)
        }
    }

}
