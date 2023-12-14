package com.ivzb.craftlog.data.mapper

import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.data.entity.ExpenseEntity

fun ExpenseEntity.toExpense(): Expense {
    return Expense(
        id = id,
        name = name,
        amount = amount,
        categoryId = categoryId,
        date = date,
    )
}

fun Expense.toExpenseEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = id ?: 0L,
        name = name,
        amount = amount,
        categoryId = categoryId,
        date = date,
    )
}
