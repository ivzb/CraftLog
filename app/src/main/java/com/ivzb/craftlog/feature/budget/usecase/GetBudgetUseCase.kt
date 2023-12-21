package com.ivzb.craftlog.feature.budget.usecase

import com.ivzb.craftlog.domain.model.BudgetOverview
import com.ivzb.craftlog.domain.repository.BudgetRepository
import com.ivzb.craftlog.domain.repository.ExpenseRepository
import com.ivzb.craftlog.domain.repository.InvestmentRepository
import com.ivzb.craftlog.util.zip
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class GetBudgetUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val expenseRepository: ExpenseRepository,
    private val investmentRepository: InvestmentRepository
) {

    suspend fun getBudgetOverview(): Flow<BudgetOverview> {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

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
