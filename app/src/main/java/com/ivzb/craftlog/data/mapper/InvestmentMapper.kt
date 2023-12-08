package com.ivzb.craftlog.data.mapper

import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.data.entity.InvestmentEntity

fun InvestmentEntity.toInvestment(): Investment {
    return Investment(
        id = id,
        name = name,
        amount = amount,
        cost = cost,
        category = category,
        date = date,
    )
}

fun Investment.toInvestmentEntity(): InvestmentEntity {
    return InvestmentEntity(
        id = id ?: 0L,
        name = name,
        amount = amount,
        cost = cost,
        category = category,
        date = date,
    )
}
