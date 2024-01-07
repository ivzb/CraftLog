package com.ivzb.craftlog.feature.budgetdetail

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.feature.budget.BUDGET
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination
import com.ivzb.craftlog.navigation.getItem

object BudgetDetailDestination : CraftLogNavigationDestination {

    override val route = "budget_detail_route"
    override val destination = "budget_detail_destination"

}

fun NavGraphBuilder.budgetDetailGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {

    composable(
        route = BudgetDetailDestination.route,
    ) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        val budget = navController.getItem<Budget>(BUDGET)

        BudgetDetailRoute(navController, budget)
    }
}
