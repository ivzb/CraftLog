package com.ivzb.craftlog.feature.investmentdetail

import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.investments.INVESTMENT
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object InvestmentDetailDestination : CraftLogNavigationDestination {

    override val route = "investment_detail_route"
    override val destination = "investment_detail_destination"

}

fun NavGraphBuilder.investmentDetailGraph(
    navController: NavController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    navigateBack: () -> Unit
) {

    composable(
        route = InvestmentDetailDestination.route,
    ) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }
        val investmentBundle = navController.previousBackStackEntry?.savedStateHandle?.get<Bundle>(
            INVESTMENT
        )
        val investment = investmentBundle?.getParcelable<Investment>(INVESTMENT)
        InvestmentDetailRoute(investment, navigateBack)
    }
}
