package com.ivzb.craftlog.feature.investments

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.addeditinvestment.navigation.AddInvestmentDestination
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

const val INVESTMENT = "investment"

object InvestmentsDestination : CraftLogNavigationDestination {

    override val route = "investment_route"

    override val destination = "investment_destination"
}

fun NavGraphBuilder.investmentsGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {
    composable(route = InvestmentsDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = FabBehaviour(
                visibility = true,
                textId = R.string.add_investment,
                icon = Icons.Default.Add,
                analyticsEvent = AnalyticsEvents.ADD_INVESTMENT_CLICKED_FAB,
                destinationRoute = AddInvestmentDestination.route,
            )
        }

        InvestmentsRoute(navController)
    }
}