package com.ivzb.craftlog.feature.investments.viewmodel

import com.ivzb.craftlog.domain.model.Investment

data class InvestmentsState(
    val investments: List<Investment> = emptyList()
)
