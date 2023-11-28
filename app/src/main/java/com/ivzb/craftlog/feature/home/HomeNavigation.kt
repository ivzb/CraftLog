package com.ivzb.craftlog.feature.home

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object HomeDestination : CraftLogNavigationDestination {

    override val route = "home_route"

    override val destination = "home_destination"

}

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    bottomBarVisibility: MutableState<Boolean>,
    fabVisibility: MutableState<Boolean>,
    navigateToExpenseDetail: (Expense) -> Unit
) {
    composable(route = HomeDestination.route) {
        LaunchedEffect(Unit) {
            bottomBarVisibility.value = true
            fabVisibility.value = true
        }

        HomeRoute(navController, navigateToExpenseDetail)
    }
}