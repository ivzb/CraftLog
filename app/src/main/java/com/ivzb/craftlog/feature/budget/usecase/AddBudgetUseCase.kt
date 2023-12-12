package com.ivzb.craftlog.feature.budget.usecase

import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.domain.repository.BudgetRepository
import javax.inject.Inject

class AddBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {

    suspend fun addBudget(budget: Budget) {
        repository.insertBudget(budget)
    }
}
