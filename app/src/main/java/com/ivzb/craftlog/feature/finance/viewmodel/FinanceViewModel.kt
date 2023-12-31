package com.ivzb.craftlog.feature.finance.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.budget.usecase.GetBudgetUseCase
import com.ivzb.craftlog.feature.expenses.usecase.DeleteExpenseUseCase
import com.ivzb.craftlog.feature.expenses.usecase.GetExpensesUseCase
import com.ivzb.craftlog.feature.investments.usecase.DeleteInvestmentUseCase
import com.ivzb.craftlog.feature.investments.usecase.GetInvestmentsUseCase
import com.ivzb.craftlog.util.zip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val getBudgetUseCase: GetBudgetUseCase,
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getInvestmentsUseCase: GetInvestmentsUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val deleteInvestmentUseCase: DeleteInvestmentUseCase,
) : ViewModel() {

    var state by mutableStateOf(FinanceState())
        private set

    fun load() {
        viewModelScope.launch {
            val budget = getBudgetUseCase.getBudgetOverview()
            val expenses = getExpensesUseCase.getExpenses()
            val investments = getInvestmentsUseCase.getInvestments()

            zip(budget, expenses, investments) { budget, expenses, investments ->
                FinanceState(
                    budget,
                    expenses.sortedByDescending { it.date }.take(LIMIT),
                    investments.sortedByDescending { it.date }.take(LIMIT),
                    loading = false
                )
            }.onEach {
                state = it
            }.launchIn(viewModelScope)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            deleteExpenseUseCase.deleteExpense(expense.copy())
        }
    }

    fun deleteInvestment(investment: Investment) {
        viewModelScope.launch {
            deleteInvestmentUseCase.deleteInvestment(investment.copy())
        }
    }

    companion object {

        private const val LIMIT = 5
    }

}
