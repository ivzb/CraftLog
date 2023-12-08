package com.ivzb.craftlog.feature.addinvestment.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.addexpense.usecase.AddInvestmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddInvestmentViewModel @Inject constructor(
    private val addInvestmentUseCase: AddInvestmentUseCase
) : ViewModel() {

    private val _isInvestmentSaved = MutableSharedFlow<Unit>()
    val isInvestmentSaved = _isInvestmentSaved.asSharedFlow()

    fun createInvestment(
        name: String,
        amount: BigDecimal,
        cost: BigDecimal,
        date: Date
    ): Investment {
        return Investment(
            id = 0,
            name = name,
            amount = amount,
            cost = cost,
            date = date,
        )
    }

    fun addInvestment(context: Context, state: AddInvestmentState) {
        viewModelScope.launch {
            val investment = state.investment
            val investmentAdded = addInvestmentUseCase.addInvestment(investment)
            _isInvestmentSaved.emit(investmentAdded)
        }
    }

}
