package com.ivzb.craftlog.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.feature.addexpense.navigation.addExpenseGraph
import com.ivzb.craftlog.feature.budget.BUDGET
import com.ivzb.craftlog.feature.budget.budgetGraph
import com.ivzb.craftlog.feature.budgetdetail.BudgetDetailDestination
import com.ivzb.craftlog.feature.budgetdetail.budgetDetailGraph
import com.ivzb.craftlog.feature.expenseconfirm.navigation.ExpenseConfirmDestination
import com.ivzb.craftlog.feature.expenseconfirm.navigation.expenseConfirmGraph
import com.ivzb.craftlog.feature.expensedetail.ExpenseDetailDestination
import com.ivzb.craftlog.feature.expensedetail.expenseDetailGraph
import com.ivzb.craftlog.feature.expenses.EXPENSE
import com.ivzb.craftlog.feature.expenses.expensesGraph
import com.ivzb.craftlog.feature.home.HomeDestination
import com.ivzb.craftlog.feature.home.homeGraph
import com.ivzb.craftlog.feature.investments.INVESTMENT
import com.ivzb.craftlog.feature.investments.investmentsGraph
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
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            navigateToExpenseDetail = {
                val bundle = Bundle()
                bundle.putParcelable(EXPENSE, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(EXPENSE, bundle)
                }
                navController.navigate(ExpenseDetailDestination.route)
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
            }
        )

        expenseDetailGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            onBackClicked = { navController.navigateUp() }
        )

        addExpenseGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            onBackClicked = { navController.navigateUp() },
            navigateToExpenseConfirm = {
                // TODO: Replace with expense id
                val bundle = Bundle()
                bundle.putParcelable(EXPENSE, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(EXPENSE, bundle)
                }
                navController.navigate(ExpenseConfirmDestination.route)
            }
        )

        expenseConfirmGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            onBackClicked = { navController.navigateUp() },
            navigateToHome = {
                navController.navigateSingleTop(HomeDestination.route)
            }
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
            }
        )

        budgetDetailGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
            onBackClicked = { navController.navigateUp() }
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
//                navController.navigate(InvestmentDetailDestination.route)
            }
        )
    }
}