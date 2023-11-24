package com.waseefakhtar.doseapp.data.repository

import com.ivzb.craftlog.data.ExpenseDao
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.repository.ExpenseRepository
import com.waseefakhtar.doseapp.data.mapper.toExpense
import com.waseefakhtar.doseapp.data.mapper.toExpenseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao
) : ExpenseRepository {

    override suspend fun insertExpenses(expenses: List<Expense>) {
        expenses.map { it.toExpenseEntity() }.forEach {
            dao.insertExpense(it)
        }
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
}
