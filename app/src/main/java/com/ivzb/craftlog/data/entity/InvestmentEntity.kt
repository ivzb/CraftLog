package com.ivzb.craftlog.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

@Entity
data class InvestmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val amount: BigDecimal,
    val cost: BigDecimal,
    val categoryId: String,
    val date: Date,
    val additionalData: Map<String, String>
)