package com.ivzb.craftlog.feature.addexpense.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.addexpense.usecase.AddExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModel() {

    private val _isExpenseSaved = MutableSharedFlow<Unit>()
    val isExpenseSaved = _isExpenseSaved.asSharedFlow()

    fun createExpense(
        name: String,
        amount: BigDecimal,
        category: String,
        date: Date
    ): Expense {
        return Expense(
            id = 0,
            name = name,
            amount = amount,
            category = category,
            date = date,
        )
    }

    fun addExpense(context: Context, state: AddExpenseState) {
        viewModelScope.launch {
            val expense = state.expense
            val expenseAdded = addExpenseUseCase.addExpense(expense)
            _isExpenseSaved.emit(expenseAdded)
        }
    }

}
