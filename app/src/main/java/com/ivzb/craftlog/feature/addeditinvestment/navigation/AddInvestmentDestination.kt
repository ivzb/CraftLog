package com.ivzb.craftlog.feature.addeditinvestment.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.feature.addeditinvestment.AddEditInvestmentRoute
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object AddInvestmentDestination : CraftLogNavigationDestination {

    override val route = "add_investment_route"

    override val destination = "add_investment_destination"

}

fun NavGraphBuilder.addInvestmentGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {
    composable(route = AddInvestmentDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        AddEditInvestmentRoute(navController, null)
    }
}
