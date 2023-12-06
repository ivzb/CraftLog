package com.ivzb.craftlog.feature.addinvestment.viewmodel

import androidx.lifecycle.ViewModel
import com.ivzb.craftlog.domain.model.Investment
import java.math.BigDecimal
import java.util.Date

class AddInvestmentViewModel : ViewModel() {

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

}
