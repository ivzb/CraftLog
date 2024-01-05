package com.ivzb.craftlog.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.feature.addeditexpense.navigation.AddExpenseDestination
import com.ivzb.craftlog.feature.addeditexpense.navigation.EditExpenseDestination
import com.ivzb.craftlog.feature.addeditexpense.navigation.addExpenseGraph
import com.ivzb.craftlog.feature.addeditexpense.navigation.editExpenseGraph
import com.ivzb.craftlog.feature.addeditinvestment.navigation.AddInvestmentDestination
import com.ivzb.craftlog.feature.addeditinvestment.navigation.EditInvestmentDestination
import com.ivzb.craftlog.feature.addeditinvestment.navigation.addInvestmentGraph
import com.ivzb.craftlog.feature.addeditinvestment.navigation.editInvestmentGraph
import com.ivzb.craftlog.feature.addnote.navigation.AddNoteDestination
import com.ivzb.craftlog.feature.addnote.navigation.addNoteGraph
import com.ivzb.craftlog.feature.budget.BUDGET
import com.ivzb.craftlog.feature.budget.BudgetDestination
import com.ivzb.craftlog.feature.budget.budgetGraph
import com.ivzb.craftlog.feature.budgetdetail.BudgetDetailDestination
import com.ivzb.craftlog.feature.budgetdetail.budgetDetailGraph
import com.ivzb.craftlog.feature.expensedetail.ExpenseDetailDestination
import com.ivzb.craftlog.feature.expensedetail.expenseDetailGraph
import com.ivzb.craftlog.feature.expenses.EXPENSE
import com.ivzb.craftlog.feature.expenses.ExpensesDestination
import com.ivzb.craftlog.feature.expenses.expensesGraph
import com.ivzb.craftlog.feature.finance.financeGraph
import com.ivzb.craftlog.feature.home.HomeDestination
import com.ivzb.craftlog.feature.home.homeGraph
import com.ivzb.craftlog.feature.investmentdetail.InvestmentDetailDestination
import com.ivzb.craftlog.feature.investmentdetail.investmentDetailGraph
import com.ivzb.craftlog.feature.investments.INVESTMENT
import com.ivzb.craftlog.feature.investments.InvestmentsDestination
import com.ivzb.craftlog.feature.investments.investmentsGraph
import com.ivzb.craftlog.feature.notedetail.NoteDetailDestination
import com.ivzb.craftlog.feature.notedetail.noteDetailGraph
import com.ivzb.craftlog.feature.notes.NOTE
import com.ivzb.craftlog.feature.notes.NotesDestination
import com.ivzb.craftlog.feature.notes.notesGraph
import com.ivzb.craftlog.util.navigateSingleTop

@Composable
fun CraftLogNavHost(
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateToExpenses = {
                navController.navigate(ExpensesDestination.route)
            },
            navigateToExpenseDetail = {
                val bundle = Bundle()
                bundle.putParcelable(EXPENSE, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(EXPENSE, bundle)
                }
                navController.navigate(ExpenseDetailDestination.route)
            },
            navigateToAddExpense = {
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(EXPENSE, null)
                }
                navController.navigate(AddExpenseDestination.route)
            },
            navigateToEditExpense = {
                val bundle = Bundle()
                bundle.putParcelable(EXPENSE, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(EXPENSE, bundle)
                }
                navController.navigate(EditExpenseDestination.route)
            },
            navigateToInvestments = {
                navController.navigate(InvestmentsDestination.route)
            },
            navigateToInvestmentDetail = {
                val bundle = Bundle()
                bundle.putParcelable(INVESTMENT, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(INVESTMENT, bundle)
                }
                navController.navigate(InvestmentDetailDestination.route)
            },
            navigateToAddInvestment = {
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(INVESTMENT, null)
                }
                navController.navigate(AddInvestmentDestination.route)
            },
            navigateToEditInvestment = {
                val bundle = Bundle()
                bundle.putParcelable(INVESTMENT, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(INVESTMENT, bundle)
                }
                navController.navigate(EditInvestmentDestination.route)
            },
            navigateToNotes = {
                navController.navigateSingleTop(NotesDestination.route)
            },
            navigateToNoteDetail = {
                val bundle = Bundle()
                bundle.putParcelable(NOTE, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(NOTE, bundle)
                }
                navController.navigate(NoteDetailDestination.route)
            },
            navigateToAddNote = {
                navController.navigate(AddNoteDestination.route)
            },
        )

        financeGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateToBudget = {
               navController.navigate(BudgetDestination.route)
            },
            navigateToBudgetDetail = {
                val bundle = Bundle()
                bundle.putParcelable(BUDGET, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(BUDGET, bundle)
                }
                navController.navigate(BudgetDetailDestination.route)
            },
            navigateToExpenses = {
                navController.navigate(ExpensesDestination.route)
            },
            navigateToExpenseDetail = {
                val bundle = Bundle()
                bundle.putParcelable(EXPENSE, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(EXPENSE, bundle)
                }
                navController.navigate(ExpenseDetailDestination.route)
            },
            navigateToAddExpense = {
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(EXPENSE, null)
                }
                navController.navigate(AddExpenseDestination.route)
            },
            navigateToEditExpense = {
                val bundle = Bundle()
                bundle.putParcelable(EXPENSE, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(EXPENSE, bundle)
                }
                navController.navigate(EditExpenseDestination.route)
            },
            navigateToInvestments = {
                navController.navigate(InvestmentsDestination.route)
            },
            navigateToInvestmentDetail = {
                val bundle = Bundle()
                bundle.putParcelable(INVESTMENT, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(INVESTMENT, bundle)
                }
                navController.navigate(InvestmentDetailDestination.route)
            },
            navigateToAddInvestment = {
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(INVESTMENT, null)
                }
                navController.navigate(AddInvestmentDestination.route)
            },
            navigateToEditInvestment = {
                val bundle = Bundle()
                bundle.putParcelable(INVESTMENT, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(INVESTMENT, bundle)
                }
                navController.navigate(EditInvestmentDestination.route)
            }
        )

        expensesGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateToExpenseDetail = {
                val bundle = Bundle()
                bundle.putParcelable(EXPENSE, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(EXPENSE, bundle)
                }
                navController.navigate(ExpenseDetailDestination.route)
            },
            navigateToEditExpense = {
                val bundle = Bundle()
                bundle.putParcelable(EXPENSE, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(EXPENSE, bundle)
                }
                navController.navigate(EditExpenseDestination.route)
            },
            navigateBack = { navController.navigateUp() }
        )

        expenseDetailGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateBack = { navController.navigateUp() }
        )

        addExpenseGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateBack = { navController.navigateUp() },
        )

        editExpenseGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateBack = { navController.navigateUp() },
        )

        budgetGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateToBudgetDetail = {
                val bundle = Bundle()
                bundle.putParcelable(BUDGET, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(BUDGET, bundle)
                }
                navController.navigate(BudgetDetailDestination.route)
            },
            navigateBack = { navController.navigateUp() }
        )

        budgetDetailGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateBack = { navController.navigateUp() }
        )

        investmentsGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateToInvestmentDetail = {
                val bundle = Bundle()
                bundle.putParcelable(INVESTMENT, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(INVESTMENT, bundle)
                }
                navController.navigate(InvestmentDetailDestination.route)
            },
            navigateToEditInvestment = {
                val bundle = Bundle()
                bundle.putParcelable(INVESTMENT, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(INVESTMENT, bundle)
                }
                navController.navigate(EditInvestmentDestination.route)
            },
            navigateBack = { navController.navigateUp() }
        )

        investmentDetailGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateBack = { navController.navigateUp() }
        )

        addInvestmentGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateBack = { navController.navigateUp() },
        )

        editInvestmentGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateBack = { navController.navigateUp() },
        )

        notesGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateToNoteDetail = {
                val bundle = Bundle()
                bundle.putParcelable(NOTE, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(NOTE, bundle)
                }
                navController.navigate(NoteDetailDestination.route)
            }
        )

        noteDetailGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateBack = { navController.navigateUp() }
        )

        addNoteGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateBack = { navController.navigateUp() },
            navigateToNotes = {
                navController.navigateSingleTop(NotesDestination.route)
            }
        )
    }
}