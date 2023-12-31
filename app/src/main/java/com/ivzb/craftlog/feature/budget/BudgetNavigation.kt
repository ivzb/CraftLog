package com.ivzb.craftlog.feature.budget

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

const val BUDGET = "budget"

object BudgetDestination : CraftLogNavigationDestination {

    override val route = "budget_route"

    override val destination = "budget_destination"
}

fun NavGraphBuilder.budgetGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {
    composable(route = BudgetDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        BudgetRoute(navController)
    }
}