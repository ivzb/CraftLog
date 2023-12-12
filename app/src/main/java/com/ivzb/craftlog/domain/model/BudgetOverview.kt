package com.ivzb.craftlog.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class BudgetOverview(
    val budget: Budget,
    val expenses: List<Expense>,
    val investments: List<Investment>,
) : Parcelable {

    val balance: BigDecimal by lazy {
        income - spent - saved
    }

    val income: BigDecimal by lazy {
        budget.income ?: 0.toBigDecimal()
    }

    val spent: BigDecimal by lazy {
        expenses.sumOf { it.amount }
    }

    val invested = investments.sumOf { it.amount }

    // todo
    val saved = 567.34.toBigDecimal()

    // todo: provide these as parameter which will be calculated by the use case

    val emergencyFund: BigDecimal = BigDecimal.ZERO

    val costOfLiving: BigDecimal = BigDecimal.ZERO

    val mortgage: BigDecimal = BigDecimal.ZERO

}
