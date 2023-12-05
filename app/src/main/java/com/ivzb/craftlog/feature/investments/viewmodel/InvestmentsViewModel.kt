package com.ivzb.craftlog.feature.investments.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.feature.investments.usecase.GetInvestmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvestmentsViewModel @Inject constructor(
    private val getInvestmentsUseCase: GetInvestmentsUseCase
) : ViewModel() {

    var state by mutableStateOf(InvestmentsState())
        private set

    init {
        loadInvestments()
    }

    fun loadInvestments() {
        viewModelScope.launch {
            getInvestmentsUseCase.getInvestments().onEach { investmentsList ->
                state = state.copy(
                    investments = investmentsList
                )
            }.launchIn(viewModelScope)
        }
    }
}
