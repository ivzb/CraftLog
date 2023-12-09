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
    val invested: BigDecimal,
) : Parcelable {

    val balance: BigDecimal by lazy {
        income - spent - saved
    }

    // todo: provide these as parameter which will be calculated by the use case

    val bankStart: BigDecimal = BigDecimal.ZERO

    val bankEnd: BigDecimal = BigDecimal.ZERO

    val emergencyFund: BigDecimal = BigDecimal.ZERO

    val costOfLiving: BigDecimal = BigDecimal.ZERO

    val mortgage: BigDecimal = BigDecimal.ZERO

}
