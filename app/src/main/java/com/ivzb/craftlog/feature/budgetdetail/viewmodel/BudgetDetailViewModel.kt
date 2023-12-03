package com.ivzb.craftlog.feature.budgetdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.feature.budget.usecase.UpdateBudgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetDetailViewModel @Inject constructor(
    private val updateBudgetUseCase: UpdateBudgetUseCase
) : ViewModel() {

    fun updateBudget(budget: Budget) {
        viewModelScope.launch {
            updateBudgetUseCase.updateBudget(budget.copy())
        }
    }
}
