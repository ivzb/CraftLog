package com.ivzb.craftlog.util

enum class ExpenseCategory {
    None,
    Bills,
    Food,
    Car,
    Fuel,
    Delivery,
    Mortgage,
    Apartment,
    House,
    Entertainment,
    Other,
}

fun getExpenseCategoryList(): List<ExpenseCategory> {
    return ExpenseCategory.values().toList()
}