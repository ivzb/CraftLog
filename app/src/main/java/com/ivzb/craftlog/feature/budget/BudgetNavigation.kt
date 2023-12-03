package com.ivzb.craftlog.feature.budget

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

const val BUDGET = "budget"

object BudgetDestination : CraftLogNavigationDestination {

    override val route = "budget_route"

    override val destination = "budget_destination"
}

fun NavGraphBuilder.budgetGraph(
    bottomBarVisibility: MutableState<Boolean>,
    fabVisibility: MutableState<Boolean>,
    navigateToBudgetDetail: (Budget) -> Unit
) {
    composable(route = BudgetDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = true
            fabVisibility.value = false
        }

        BudgetRoute(navigateToBudgetDetail)
    }
}