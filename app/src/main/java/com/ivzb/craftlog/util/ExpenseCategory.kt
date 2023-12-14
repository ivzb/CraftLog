package com.ivzb.craftlog.util

import android.content.Context
import com.ivzb.craftlog.R

enum class ExpenseCategory(val id: String, val titleResId: Int) {
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

    companion object {

        fun find(id: String): ExpenseCategory {
            return entries.find { it.id == id } ?: None
        }

    }
}

fun getExpenseCategoryEntities(context: Context): List<ItemEntity> {
    return ExpenseCategory.entries.map {
        ItemEntity(it.id, context.getString(it.titleResId))
    }
}