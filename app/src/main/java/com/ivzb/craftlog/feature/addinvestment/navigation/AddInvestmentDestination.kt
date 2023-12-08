package com.ivzb.craftlog.feature.addinvestment.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.addinvestment.AddInvestmentRoute
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object AddInvestmentDestination : CraftLogNavigationDestination {

    override val route = "add_investment_route"
    override val destination = "add_investment_destination"

}

fun NavGraphBuilder.addInvestmentGraph(
    navController: NavController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    onBackClicked: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable(route = AddInvestmentDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        AddInvestmentRoute(onBackClicked, navigateToHome)
    }
}
