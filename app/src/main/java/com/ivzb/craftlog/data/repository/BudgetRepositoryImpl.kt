package com.ivzb.craftlog.data.repository

import com.ivzb.craftlog.data.dao.BudgetDao
import com.ivzb.craftlog.data.mapper.toBudget
import com.ivzb.craftlog.data.mapper.toBudgetEntity
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BudgetRepositoryImpl(
    private val dao: BudgetDao
) : BudgetRepository {

    override suspend fun insertBudget(budget: Budget) {
        val entity = budget.toBudgetEntity()
        dao.insertBudget(entity)
    }

    override suspend fun deleteBudget(budget: Budget) {
        dao.deleteBudget(budget.toBudgetEntity())
    }

    override suspend fun updateBudget(budget: Budget) {
        dao.updateBudget(budget.toBudgetEntity())
    }

    override fun getBudgetForDate(year: Int, month: Int): Flow<Budget> {
        return dao.getBudgetForDate(year, month).map {
            it?.toBudget() ?: Budget(
                id = -1,
                year = year,
                month = month,
                income = null,
                emergencyFund = null,
                mortgage = null,
                bankStart = null,
                bankEnd = null
            )
        }
    }
}
