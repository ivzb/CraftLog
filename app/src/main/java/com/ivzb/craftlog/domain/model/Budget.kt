package com.ivzb.craftlog.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Budget(
    val id: Long?,
    val year: Int,
    val month: Int,
    val income: BigDecimal?,
    val emergencyFund: BigDecimal?,
    val mortgage: BigDecimal?,
    val bankStart: BigDecimal?,
    val bankEnd: BigDecimal?,
) : Parcelable
