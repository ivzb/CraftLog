package com.ivzb.craftlog.feature.budgetdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.feature.budget.usecase.AddBudgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class BudgetDetailViewModel @Inject constructor(
    private val addBudgetUseCase: AddBudgetUseCase
) : ViewModel() {

    private val _isBudgetSaved = MutableSharedFlow<Unit>()
    val isBudgetSaved = _isBudgetSaved.asSharedFlow()

    fun createBudget(
        id: Long?,
        year: Int,
        month: Int,
        income: BigDecimal?,
        bankStart: BigDecimal?,
        bankEnd: BigDecimal?,
    ): Budget {
        return Budget(
            id,
            year,
            month,
            income,
            bankStart,
            bankEnd
        )
    }

    fun addBudget(budgetState: BudgetDetailState) {
        viewModelScope.launch {
            val budget = budgetState.budget
            val budgetAdded = addBudgetUseCase.addBudget(budget.copy())
            _isBudgetSaved.emit(budgetAdded)
        }
    }
}
