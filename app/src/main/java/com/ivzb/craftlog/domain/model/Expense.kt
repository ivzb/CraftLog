package com.ivzb.craftlog.domain.model

import android.os.Parcelable
import com.ivzb.craftlog.util.ExpenseCategory
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.Date

@Parcelize
data class Expense(
    val id: Long?,
    val name: String,
    val amount: BigDecimal,
    val categoryId: String,
    val date: Date,
    val additionalData: Map<String, String>
) : Parcelable {

    @IgnoredOnParcel
    val category: ExpenseCategory = ExpenseCategory.find(categoryId)

    override fun toString() = "$name (${category.name})"

}
