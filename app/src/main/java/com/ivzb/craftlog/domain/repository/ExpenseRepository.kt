package com.ivzb.craftlog.domain.repository

import com.ivzb.craftlog.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ExpenseRepository {

    suspend fun insertExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)

    suspend fun updateExpense(expense: Expense)

    fun getAllExpenses(): Flow<List<Expense>>

    fun getExpensesForDate(date: Date): Flow<List<Expense>>

    fun getExpensesForRange(year: Int, month: Int): Flow<List<Expense>>

    fun findExpenses(name: String): Flow<List<Expense>>
}
