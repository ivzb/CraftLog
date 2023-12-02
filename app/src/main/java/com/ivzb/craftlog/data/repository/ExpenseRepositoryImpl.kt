package com.ivzb.craftlog.data.repository

import com.ivzb.craftlog.data.ExpenseDao
import com.ivzb.craftlog.data.mapper.toExpense
import com.ivzb.craftlog.data.mapper.toExpenseEntity
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.repository.ExpenseRepository
import com.ivzb.craftlog.util.getDateRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao
) : ExpenseRepository {

    override suspend fun insertExpense(expense: Expense) {
        val entity = expense.toExpenseEntity()
        dao.insertExpense(entity)
    }

    override suspend fun deleteExpense(expense: Expense) {
        dao.deleteExpense(expense.toExpenseEntity())
    }

    override suspend fun updateExpense(expense: Expense) {
        dao.updateExpense(expense.toExpenseEntity())
    }

    override fun getAllExpenses(): Flow<List<Expense>> {
        return dao.getAllExpenses().map { entities ->
            entities.map { it.toExpense() }
        }
    }

    override fun getExpensesForDate(date: Date): Flow<List<Expense>> {
        return dao.getExpensesForDate(
            date = date
        ).map { entities ->
            entities.map { it.toExpense() }
        }
    }

    override fun getExpensesForRange(year: Int, month: Int): Flow<List<Expense>> {
        val (startDate, endDate) = getDateRange(year, month)

        return dao.getExpensesForDateRange(
            startDate,
            endDate
        ).map { entities ->
            entities.map { it.toExpense() }
        }
    }
}
