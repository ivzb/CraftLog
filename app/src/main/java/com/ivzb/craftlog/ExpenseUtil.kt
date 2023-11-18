package com.ivzb.craftlog

enum class ExpenseCategory {
    Empty,
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