package com.ivzb.craftlog.feature.expenseconfirm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.feature.expenseconfirm.usecase.AddExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseConfirmViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModel() {

    private val _isExpenseSaved = MutableSharedFlow<Unit>()
    val isExpenseSaved = _isExpenseSaved.asSharedFlow()

    fun addExpense(context: Context, state: ExpenseConfirmState) {
        viewModelScope.launch {
            val expense = state.expense
            val expenseAdded = addExpenseUseCase.addExpense(expense)
            _isExpenseSaved.emit(expenseAdded)
        }
    }
}
