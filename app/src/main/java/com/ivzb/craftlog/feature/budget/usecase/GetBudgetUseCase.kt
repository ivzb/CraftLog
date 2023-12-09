package com.ivzb.craftlog.feature.budget.usecase

import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.domain.repository.ExpenseRepository
import com.ivzb.craftlog.domain.repository.InvestmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetBudgetUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val investmentRepository: InvestmentRepository
) {

    suspend fun getBudget(year: Int, month: Int): Flow<Budget> {
        val expenses = expenseRepository.getExpensesForRange(year, month)
        val investments = investmentRepository.getInvestmentsForRange(year, month)

        return expenses.zip(investments) { expenses, investments ->
            val spent = expenses.sumOf { it.amount }
            val invested = investments.sumOf { it.amount }

            // todo: compute budget out of filtered expenses
            Budget(
                year,
                month,
                // todo: budget details
                income = 1234.56.toBigDecimal(),
                spent = spent,
                // todo: budget details
                saved = 723.56.toBigDecimal(),
                invested = invested
            )
        }
    }
}
