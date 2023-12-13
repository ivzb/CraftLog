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

    val balance: BigDecimal
        get() = income - spent - saved

    val income: BigDecimal
        get() = budget.income ?: 0.toBigDecimal()

    val spent: BigDecimal
        get() = expenses.sumOf { it.amount }

    val invested: BigDecimal
        get() = investments.sumOf { it.amount }

    // todo
    val saved: BigDecimal
        get() = 567.34.toBigDecimal()

    val emergencyFund: BigDecimal
        get() = budget.emergencyFund ?: 0.toBigDecimal()

    // todo: provide these as parameter which will be calculated by the use case

    val costOfLiving: BigDecimal
        get() = BigDecimal.ZERO

    val mortgage: BigDecimal
        get() = BigDecimal.ZERO

}
