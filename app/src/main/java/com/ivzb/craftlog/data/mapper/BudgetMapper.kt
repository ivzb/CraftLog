package com.ivzb.craftlog.data.mapper

import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.data.entity.BudgetEntity

fun BudgetEntity.toBudget(): Budget {
    return Budget(
        id = id,
        year = year,
        month = month,
        income = income,
        bankStart = bankStart,
        bankEnd = bankEnd,
    )
}

fun Budget.toBudgetEntity(): BudgetEntity {
    return BudgetEntity(
        id = id ?: 0L,
        year = year,
        month = month,
        income = income ?: 0.toBigDecimal(),
        bankStart = bankStart ?: 0.toBigDecimal(),
        bankEnd = bankEnd ?: 0.toBigDecimal(),
    )
}
