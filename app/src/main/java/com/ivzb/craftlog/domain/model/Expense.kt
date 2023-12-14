package com.ivzb.craftlog.domain.model

import android.os.Parcelable
import com.ivzb.craftlog.util.ExpenseCategory
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.Date

@Parcelize
data class Expense(
    val id: Long?,
    val name: String,
    val amount: BigDecimal,
    val categoryId: String,
    val date: Date
) : Parcelable {

    val category: ExpenseCategory = ExpenseCategory.find(categoryId)

}
