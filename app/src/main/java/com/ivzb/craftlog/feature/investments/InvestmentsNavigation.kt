package com.ivzb.craftlog.feature.investments

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.addexpense.navigation.AddExpenseDestination
import com.ivzb.craftlog.feature.addinvestment.navigation.AddInvestmentDestination
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

const val INVESTMENT = "investment"

object InvestmentsDestination : CraftLogNavigationDestination {

    override val route = "investment_route"

    override val destination = "investment_destination"
}

fun NavGraphBuilder.investmentsGraph(
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    navigateToInvestmentDetail: (Investment) -> Unit
) {
    composable(route = InvestmentsDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = true
            fabBehaviour.value = FabBehaviour(
                visibility = true,
                textId = R.string.add_investment,
                icon = Icons.Default.Add,
                analyticsEvent = AnalyticsEvents.ADD_INVESTMENT_CLICKED_FAB,
                destinationRoute = AddInvestmentDestination.route,
            )
        }

        InvestmentsRoute(navigateToInvestmentDetail)
    }
}