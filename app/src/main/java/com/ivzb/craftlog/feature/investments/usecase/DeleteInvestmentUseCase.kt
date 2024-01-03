package com.ivzb.craftlog.feature.investments.usecase

import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.domain.repository.InvestmentRepository
import javax.inject.Inject

class DeleteInvestmentUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {

    suspend fun deleteInvestment(investment: Investment) {
        return repository.deleteInvestment(investment)
    }
}
