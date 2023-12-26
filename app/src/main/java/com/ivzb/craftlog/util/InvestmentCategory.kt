package com.ivzb.craftlog.util

import android.content.Context
import com.ivzb.craftlog.App
import com.ivzb.craftlog.R

enum class InvestmentCategory(override val id: String, val titleResId: Int): ItemEntity {
    Stocks("stocks", R.string.stocks),
    Cryptocurrency("cryptocurrency", R.string.cryptocurrency),
    Commodity("commodity", R.string.commodity),
    EmergencyFund("emergency_fund", R.string.emergency_fund),
    Fund("fund", R.string.fund),
    Cash("cash", R.string.cash),
    Property("property", R.string.property),
    ETF("etf", R.string.etf),
    Bond("bond", R.string.bond),
    Security("security", R.string.security),
    Option("option", R.string.option),
    None("none", R.string.none);

    override val title: String
        get() = App.applicationContext().getString(titleResId);

    companion object {

        fun find(id: String): InvestmentCategory {
            return entries.find { it.id == id } ?: None
        }

    }
}