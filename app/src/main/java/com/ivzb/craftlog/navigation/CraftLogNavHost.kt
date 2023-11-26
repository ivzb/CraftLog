package com.ivzb.craftlog.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ivzb.craftlog.feature.addexpense.navigation.addExpenseGraph
import com.ivzb.craftlog.feature.expenseconfirm.navigation.ExpenseConfirmDestination
import com.ivzb.craftlog.feature.expenseconfirm.navigation.expenseConfirmGraph
import com.ivzb.craftlog.feature.expensedetails.ExpenseDetailDestination
import com.ivzb.craftlog.feature.expenses.EXPENSE
import com.ivzb.craftlog.feature.expenses.expensesGraph
import com.ivzb.craftlog.feature.home.HomeDestination
import com.ivzb.craftlog.feature.home.homeGraph
import com.ivzb.craftlog.util.navigateSingleTop

@Composable
fun CraftLogNavHost(
    bottomBarVisibility: MutableState<Boolean>,
    fabVisibility: MutableState<Boolean>,
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
            fabVisibility = fabVisibility,
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
            fabVisibility = fabVisibility,
            navigateToExpenseDetail = {
                val bundle = Bundle()
                bundle.putParcelable(EXPENSE, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(EXPENSE, bundle)
                }
                navController.navigate(ExpenseDetailDestination.route)
            }
        )
//
//        expensesDetailGraph(
//            navController = navController,
//            bottomBarVisibility = bottomBarVisibility,
//            fabVisibility = fabVisibility,
//            onBackClicked = { navController.navigateUp() }
//        )
//
//        calendarGraph(bottomBarVisibility, fabVisibility)
//
        addExpenseGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabVisibility = fabVisibility,
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
            fabVisibility = fabVisibility,
            onBackClicked = { navController.navigateUp() },
            navigateToHome = {
                navController.navigateSingleTop(HomeDestination.route)
            }
        )
    }
}