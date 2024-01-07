package com.ivzb.craftlog.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavHostController
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.addeditexpense.navigation.AddExpenseDestination
import com.ivzb.craftlog.feature.addeditexpense.navigation.EditExpenseDestination
import com.ivzb.craftlog.feature.addeditinvestment.navigation.AddInvestmentDestination
import com.ivzb.craftlog.feature.addeditinvestment.navigation.EditInvestmentDestination
import com.ivzb.craftlog.feature.addeditnote.navigation.AddNoteDestination
import com.ivzb.craftlog.feature.addeditnote.navigation.EditNoteDestination
import com.ivzb.craftlog.feature.budget.BUDGET
import com.ivzb.craftlog.feature.budget.BudgetDestination
import com.ivzb.craftlog.feature.budgetdetail.BudgetDetailDestination
import com.ivzb.craftlog.feature.expensedetail.ExpenseDetailDestination
import com.ivzb.craftlog.feature.expenses.EXPENSE
import com.ivzb.craftlog.feature.expenses.ExpensesDestination
import com.ivzb.craftlog.feature.investmentdetail.InvestmentDetailDestination
import com.ivzb.craftlog.feature.investments.INVESTMENT
import com.ivzb.craftlog.feature.investments.InvestmentsDestination
import com.ivzb.craftlog.feature.notedetail.NoteDetailDestination
import com.ivzb.craftlog.feature.notes.NOTE
import com.ivzb.craftlog.feature.notes.NotesDestination
import com.ivzb.craftlog.util.getParcelable

fun NavHostController.navigateToExpenses() {
    navigate(ExpensesDestination.route)
}

fun NavHostController.navigateToExpenseDetail(expense: Expense) {
    setItem(EXPENSE, expense)
    navigate(ExpenseDetailDestination.route)
}

fun NavHostController.navigateToAddExpense() {
    setItem(EXPENSE, null)
    navigate(AddExpenseDestination.route)
}

fun NavHostController.navigateToEditExpense(expense: Expense) {
    setItem(EXPENSE, expense)
    navigate(EditExpenseDestination.route)
}

fun NavHostController.navigateToInvestments() {
    navigate(InvestmentsDestination.route)
}

fun NavHostController.navigateToInvestmentDetail(investment: Investment) {
    setItem(INVESTMENT, investment)
    navigate(InvestmentDetailDestination.route)
}

fun NavHostController.navigateToAddInvestment() {
    setItem(INVESTMENT, null)
    navigate(AddInvestmentDestination.route)
}

fun NavHostController.navigateToEditInvestment(investment: Investment) {
    setItem(INVESTMENT, investment)
    navigate(EditInvestmentDestination.route)
}

fun NavHostController.navigateToBudget() {
    navigate(BudgetDestination.route)
}

fun NavHostController.navigateToBudgetDetail(budget: Budget) {
    setItem(BUDGET, budget)
    navigate(BudgetDetailDestination.route)
}

fun NavHostController.navigateToNotes() {
    navigateSingleTop(NotesDestination.route)
}

fun NavHostController.navigateToNoteDetail(note: Note) {
    setItem(NOTE, note)
    navigate(NoteDetailDestination.route)
}

fun NavHostController.navigateToAddNote() {
    setItem(NOTE, null)
    navigate(AddNoteDestination.route)
}

fun NavHostController.navigateToEditNote(note: Note) {
    setItem(NOTE, note)
    navigate(EditNoteDestination.route)
}

fun NavHostController.navigateBack() {
    navigateUp()
}

private fun NavHostController.navigateSingleTop(route: String) {
    this.navigate(route) {
        popUpTo(route)
        launchSingleTop = true
    }
}

private fun NavHostController.setItem(key: String, value: Parcelable?) {
    val bundle = Bundle()
    bundle.putParcelable(key, value)
    currentBackStackEntry?.savedStateHandle.apply {
        this?.set(key, bundle)
    }
}

inline fun <reified T : Parcelable> NavHostController.getItem(key: String): T? {
    val bundle: Bundle? = previousBackStackEntry?.savedStateHandle?.get<Bundle>(key)
    return getParcelable<T>(bundle, key)
}