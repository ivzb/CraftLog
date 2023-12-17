package com.ivzb.craftlog.domain.model

import android.os.Parcelable
import com.ivzb.craftlog.util.InvestmentCategory
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.Date

@Parcelize
data class Investment(
    val id: Long?,
    val name: String,
    val amount: BigDecimal,
    val cost: BigDecimal,
    val categoryId: String,
    val date: Date
) : Parcelable {

    @IgnoredOnParcel
    val category: InvestmentCategory = InvestmentCategory.find(categoryId)

    override fun toString() = "$name (${category.name})"

}
