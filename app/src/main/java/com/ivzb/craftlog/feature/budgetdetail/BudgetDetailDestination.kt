package com.ivzb.craftlog.feature.budgetdetail

import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.feature.budget.BUDGET
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object BudgetDetailDestination : CraftLogNavigationDestination {

    override val route = "budget_detail_route"
    override val destination = "budget_detail_destination"

}

fun NavGraphBuilder.budgetDetailGraph(
    navController: NavController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    navigateBack: () -> Unit
) {

    composable(
        route = BudgetDetailDestination.route,
    ) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }
        val budgetBundle = navController.previousBackStackEntry?.savedStateHandle?.get<Bundle>(
            BUDGET
        )
        val budget = budgetBundle?.getParcelable<Budget>(BUDGET)
        BudgetDetailRoute(budget, navigateBack)
    }
}
