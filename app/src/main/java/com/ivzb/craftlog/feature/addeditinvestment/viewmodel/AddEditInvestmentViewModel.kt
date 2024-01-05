package com.ivzb.craftlog.feature.addeditinvestment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.addeditinvestment.usecase.AddInvestmentUseCase
import com.ivzb.craftlog.feature.addeditinvestment.usecase.EditInvestmentUseCase
import com.ivzb.craftlog.feature.addeditinvestment.usecase.FindInvestmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddEditInvestmentViewModel @Inject constructor(
    private val addInvestmentUseCase: AddInvestmentUseCase,
    private val editInvestmentUseCase: EditInvestmentUseCase,
    private val findInvestmentsUseCase: FindInvestmentsUseCase
) : ViewModel() {

    private val _isInvestmentSaved = MutableSharedFlow<Unit>()
    val isInvestmentSaved = _isInvestmentSaved.asSharedFlow()

    private val _suggestedInvestments = MutableSharedFlow<List<Investment>>()
    val suggestedInvestments = _suggestedInvestments.asSharedFlow()

    fun createInvestment(
        id: Long,
        name: String,
        amount: BigDecimal,
        cost: BigDecimal,
        categoryId: String,
        date: Date,
        additionalData: Map<String, String>
    ): Investment {
        return Investment(
            id = id,
            name = name,
            amount = amount,
            cost = cost,
            categoryId = categoryId,
            date = date,
            additionalData = additionalData
        )
    }

    fun addInvestment(state: AddEditInvestmentState) {
        viewModelScope.launch {
            val investment = state.investment
            val investmentAdded = addInvestmentUseCase.addInvestment(investment)
            _isInvestmentSaved.emit(investmentAdded)
        }
    }

    fun editInvestment(state: AddEditInvestmentState) {
        viewModelScope.launch {
            val investment = state.investment
            val investmentEdited = editInvestmentUseCase.editInvestment(investment)
            _isInvestmentSaved.emit(investmentEdited)
        }
    }

    fun suggestInvestments(name: String) {
        viewModelScope.launch {
            val suggestedInvestments = findInvestmentsUseCase.findInvestments(name)
            _suggestedInvestments.emitAll(suggestedInvestments)
        }
    }

}
