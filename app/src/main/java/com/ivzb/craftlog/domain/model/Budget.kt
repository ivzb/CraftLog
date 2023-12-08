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

    val balance: BigDecimal by lazy {
        income - spent - saved
    }
    
    val bankStart: BigDecimal = BigDecimal.ZERO

    val bankEnd: BigDecimal = BigDecimal.ZERO

    val emergencyFund: BigDecimal = BigDecimal.ZERO

    val costOfLiving: BigDecimal = BigDecimal.ZERO

    val mortgage: BigDecimal = BigDecimal.ZERO

    val investments: BigDecimal = BigDecimal.ZERO

}
