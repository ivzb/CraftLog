package com.ivzb.craftlog.feature.investmentdetail

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.investments.INVESTMENT
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination
import com.ivzb.craftlog.navigation.getItem

object InvestmentDetailDestination : CraftLogNavigationDestination {

    override val route = "investment_detail_route"
    override val destination = "investment_detail_destination"

}

fun NavGraphBuilder.investmentDetailGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {

    composable(
        route = InvestmentDetailDestination.route,
    ) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        val investment = navController.getItem<Investment>(INVESTMENT)

        InvestmentDetailRoute(navController, investment)
    }
}
