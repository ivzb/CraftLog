package com.ivzb.craftlog.feature.investmentdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.investments.usecase.UpdateInvestmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvestmentDetailViewModel @Inject constructor(
    private val updateInvestmentUseCase: UpdateInvestmentUseCase
) : ViewModel() {

    fun updateInvestment(investment: Investment) {
        viewModelScope.launch {
            updateInvestmentUseCase.updateInvestment(investment.copy())
        }
    }
}
