package com.ivzb.craftlog.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Budget(
    val year: Int,
    val month: Int,
    val income: BigDecimal,
    val spent: BigDecimal,
    val saved: BigDecimal,
) : Parcelable {

    val total: BigDecimal by lazy {
        income - spent - saved
    }

}
