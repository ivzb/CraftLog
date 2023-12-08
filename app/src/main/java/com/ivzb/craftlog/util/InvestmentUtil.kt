package com.ivzb.craftlog.util

enum class InvestmentCategory {
    Stocks,
    Crypto,
    Commodity,
    Cash,
    Property,
    ETF,
    Bond,
    Security,
    Option,
}

fun getInvestmentCategoryList(): List<InvestmentCategory> {
    return InvestmentCategory.values().toList()
}