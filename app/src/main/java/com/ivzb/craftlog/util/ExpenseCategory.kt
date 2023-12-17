package com.ivzb.craftlog.util

import com.ivzb.craftlog.App
import com.ivzb.craftlog.R

enum class ExpenseCategory(override val id: String, private val titleResId: Int): ItemEntity {
    None("none", R.string.none),
    Bills("bills", R.string.bills),
    Food("food", R.string.food),
    Car("car", R.string.car),
    Fuel("fuel", R.string.fuel),
    Delivery("delivery", R.string.delivery),
    Mortgage("mortgage", R.string.mortgage),
    Apartment("apartment", R.string.apartment),
    House("house", R.string.house),
    Entertainment("entertainment", R.string.entertainment),
    Other("other", R.string.other);

    override val title: String
        get() = App.applicationContext().getString(titleResId);

    companion object {

        fun find(id: String): ExpenseCategory {
            return entries.find { it.id == id } ?: None
        }

    }
}