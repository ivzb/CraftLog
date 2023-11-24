package com.ivzb.craftlog.feature.expenses

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

const val EXPENSE = "expense"

object ExpensesDestination : CraftLogNavigationDestination {

    override val route = "expenses_route"

    override val destination = "expenses_destination"
}

fun NavGraphBuilder.expensesGraph(
    bottomBarVisibility: MutableState<Boolean>,
    fabVisibility: MutableState<Boolean>,
    navigateToExpenseDetail: (Expense) -> Unit
) {
    composable(route = ExpensesDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = true
            fabVisibility.value = false
        }
        ExpensesRoute(navigateToExpenseDetail)
    }
}