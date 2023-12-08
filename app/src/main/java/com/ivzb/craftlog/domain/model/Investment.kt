package com.ivzb.craftlog.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.Date

@Parcelize
data class Investment(
    val id: Long?,
    val name: String,
    val amount: BigDecimal,
    val cost: BigDecimal,
    val category: String,
    val date: Date
) : Parcelable
