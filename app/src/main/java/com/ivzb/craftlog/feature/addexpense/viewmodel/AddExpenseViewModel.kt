package com.ivzb.craftlog.feature.addexpense.viewmodel

import androidx.lifecycle.ViewModel
import com.ivzb.craftlog.domain.model.Expense
import java.math.BigDecimal
import java.util.Date

class AddExpenseViewModel : ViewModel() {

    fun createExpense(
        name: String,
        amount: BigDecimal,
        category: String,
        date: Date
    ): Expense {
        return Expense(
            id = 0,
            name = name,
            amount = amount,
            category = category,
            date = date,
        )
    }

}
