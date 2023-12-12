package com.ivzb.craftlog.feature.budget.usecase

import com.ivzb.craftlog.domain.model.BudgetOverview
import com.ivzb.craftlog.domain.repository.BudgetRepository
import com.ivzb.craftlog.domain.repository.ExpenseRepository
import com.ivzb.craftlog.domain.repository.InvestmentRepository
import com.ivzb.craftlog.util.zip
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBudgetUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val expenseRepository: ExpenseRepository,
    private val investmentRepository: InvestmentRepository
) {

    suspend fun getBudgetOverview(year: Int, month: Int): Flow<BudgetOverview> {
        val budget = budgetRepository.getBudgetForDate(year, month)
        val expenses = expenseRepository.getExpensesForRange(year, month)
        val investments = investmentRepository.getInvestmentsForRange(year, month)

        return zip(budget, expenses, investments) { budget, expenses, investments ->
            BudgetOverview(
                budget,
                expenses,
                investments
            )
        }
    }
}
