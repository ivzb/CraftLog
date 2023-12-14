package com.ivzb.craftlog.util

enum class InvestmentCategory {
    Stocks,
    Cryptocurrency,
    Commodity,
    Cash,
    Property,
    ETF,
    Bond,
    Security,
    Option,
    EmergencyFund,
}

fun getInvestmentCategoryList(): List<InvestmentCategory> {
    return InvestmentCategory.values().toList()
}