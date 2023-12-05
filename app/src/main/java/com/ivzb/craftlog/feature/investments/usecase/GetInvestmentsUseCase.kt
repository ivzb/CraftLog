package com.ivzb.craftlog.feature.investments.usecase

import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.domain.repository.InvestmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInvestmentsUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {

    suspend fun getInvestments(): Flow<List<Investment>> {
        return repository.getAllInvestments()
    }
}
