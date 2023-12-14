package com.ivzb.craftlog.util

import android.content.Context
import com.ivzb.craftlog.R

enum class InvestmentCategory(val id: String, val titleResId: Int) {
    Stocks("stocks", R.string.stocks),
    Cryptocurrency("cryptocurrency", R.string.cryptocurrency),
    Commodity("commodity", R.string.commodity),
    Cash("cash", R.string.cash),
    Property("property", R.string.property),
    ETF("etf", R.string.etf),
    Bond("bond", R.string.bond),
    Security("security", R.string.security),
    Option("option", R.string.option),
    EmergencyFund("emergency_fund", R.string.emergency_fund),
    None("none", R.string.none);

    companion object {

        fun find(id: String): InvestmentCategory {
            return entries.find { it.id == id } ?: None
        }

    }
}

fun getInvestmentCategoryEntities(context: Context): List<ItemEntity> {
    return InvestmentCategory.entries.map {
        ItemEntity(it.id, context.getString(it.titleResId))
    }
}