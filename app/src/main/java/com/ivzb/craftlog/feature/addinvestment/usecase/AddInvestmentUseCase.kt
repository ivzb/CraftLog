package com.ivzb.craftlog.feature.addinvestment.usecase

import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.domain.repository.InvestmentRepository
import javax.inject.Inject

class AddInvestmentUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {
    suspend fun addInvestment(investment: Investment) {
        repository.insertInvestment(investment)
    }
}
