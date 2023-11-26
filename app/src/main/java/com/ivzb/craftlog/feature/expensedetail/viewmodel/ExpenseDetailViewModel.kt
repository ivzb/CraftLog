package com.ivzb.craftlog.feature.expensedetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.home.usecase.UpdateExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val updateExpenseUseCase: UpdateExpenseUseCase
) : ViewModel() {

    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            updateExpenseUseCase.updateExpense(expense.copy())
        }
    }
}
