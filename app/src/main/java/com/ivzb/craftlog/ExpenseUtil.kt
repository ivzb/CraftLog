package com.ivzb.craftlog

enum class ExpenseCategory {
    None,
    Bills,
    Food,
    Car,
    Fuel,
    Apartment,
    House,
    Entertainment,
}

fun getExpenseCategoryList(): List<ExpenseCategory> {
    return ExpenseCategory.values().toList()
}