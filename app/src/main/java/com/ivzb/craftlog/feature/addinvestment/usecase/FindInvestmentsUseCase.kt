package com.ivzb.craftlog.feature.addinvestment.usecase

import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.domain.repository.InvestmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindInvestmentsUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {
    suspend fun findInvestments(name: String): Flow<List<Investment>> {
        return repository.findInvestments(name)
    }
}
