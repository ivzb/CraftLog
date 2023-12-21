package com.ivzb.craftlog.feature.budget.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.feature.budget.usecase.GetBudgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val getBudgetUseCase: GetBudgetUseCase
) : ViewModel() {

    var state by mutableStateOf(BudgetOverviewState())
        private set

    fun loadBudget() {
        viewModelScope.launch {
            getBudgetUseCase.getBudgetOverview().onEach { budgetOverview ->
                state = state.copy(
                    budgetOverview = budgetOverview
                )
            }.launchIn(viewModelScope)
        }
    }
}
