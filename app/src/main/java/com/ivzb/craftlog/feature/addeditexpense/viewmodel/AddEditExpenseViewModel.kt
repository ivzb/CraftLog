package com.ivzb.craftlog.feature.addeditexpense.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.addeditexpense.usecase.AddExpenseUseCase
import com.ivzb.craftlog.feature.addeditexpense.usecase.FindExpensesUseCase
import com.ivzb.craftlog.feature.addeditexpense.usecase.EditExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddEditExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val editExpenseUseCase: EditExpenseUseCase,
    private val findExpensesUseCase: FindExpensesUseCase
) : ViewModel() {

    private val _isExpenseSaved = MutableSharedFlow<Unit>()
    val isExpenseSaved = _isExpenseSaved.asSharedFlow()

    private val _suggestedExpenses = MutableSharedFlow<List<Expense>>()
    val suggestedExpenses = _suggestedExpenses.asSharedFlow()

    fun createExpense(
        id: Long,
        name: String,
        amount: BigDecimal,
        categoryId: String,
        date: Date,
        additionalData: Map<String, String>
    ): Expense {
        return Expense(
            id = id,
            name = name,
            amount = amount,
            categoryId = categoryId,
            date = date,
            additionalData = additionalData
        )
    }

    fun addExpense(state: AddEditExpenseState) {
        viewModelScope.launch {
            val expense = state.expense
            val expenseAdded = addExpenseUseCase.addExpense(expense)
            _isExpenseSaved.emit(expenseAdded)
        }
    }

    fun editExpense(state: AddEditExpenseState) {
        viewModelScope.launch {
            val expense = state.expense
            val expenseEdited = editExpenseUseCase.editExpense(expense)
            _isExpenseSaved.emit(expenseEdited)
        }
    }

    fun suggestExpenses(name: String) {
        viewModelScope.launch {
            val suggestedExpenses = findExpensesUseCase.findExpenses(name)
            _suggestedExpenses.emitAll(suggestedExpenses)
        }
    }

}
