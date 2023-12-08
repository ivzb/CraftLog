package com.ivzb.craftlog.feature.investments.usecase

import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.domain.repository.InvestmentRepository
import javax.inject.Inject

class UpdateInvestmentUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {

    suspend fun updateInvestment(investment: Investment) {
        return repository.updateInvestment(investment)
    }
}
