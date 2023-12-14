package com.ivzb.craftlog.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val year: Int,
    val month: Int,
    val income: BigDecimal,
    val emergencyFund: BigDecimal,
    val mortgage: BigDecimal,
    val bankStart: BigDecimal,
    val bankEnd: BigDecimal,
)
