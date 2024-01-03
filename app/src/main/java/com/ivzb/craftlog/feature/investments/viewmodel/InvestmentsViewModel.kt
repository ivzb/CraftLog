package com.ivzb.craftlog.feature.investments.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.investments.usecase.DeleteInvestmentUseCase
import com.ivzb.craftlog.feature.investments.usecase.GetInvestmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvestmentsViewModel @Inject constructor(
    private val getInvestmentsUseCase: GetInvestmentsUseCase,
    private val deleteInvestmentUseCase: DeleteInvestmentUseCase,
) : ViewModel() {

    var state by mutableStateOf(InvestmentsState())
        private set

    fun loadInvestments(filter: String = "") {
        viewModelScope.launch {
            getInvestmentsUseCase.getInvestments().onEach { investmentsList ->
                val filteredInvestmentsList = if (filter.isNotEmpty()) {
                    investmentsList.filter {
                        it.name.contains(filter, ignoreCase = true) || it.category.name.contains(filter, ignoreCase = true)
                    }
                } else {
                    investmentsList
                }

                state = state.copy(
                    investments = filteredInvestmentsList
                )
            }.launchIn(viewModelScope)
        }
    }

    fun deleteInvestment(investment: Investment) {
        viewModelScope.launch {
            deleteInvestmentUseCase.deleteInvestment(investment.copy())
        }
    }
}
