package com.ivzb.craftlog.feature.addeditinvestment.usecase

import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.domain.repository.InvestmentRepository
import javax.inject.Inject

class EditInvestmentUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {

    suspend fun editInvestment(investment: Investment) {
        return repository.updateInvestment(investment)
    }
}
