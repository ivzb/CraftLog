package com.ivzb.craftlog.feature.investments

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

const val INVESTMENT = "investment"

object InvestmentsDestination : CraftLogNavigationDestination {

    override val route = "investment_route"

    override val destination = "investment_destination"
}

fun NavGraphBuilder.investmentsGraph(
    bottomBarVisibility: MutableState<Boolean>,
    fabVisibility: MutableState<Boolean>,
    navigateToInvestmentDetail: (Investment) -> Unit
) {
    composable(route = InvestmentsDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = true
            fabVisibility.value = true
        }

        InvestmentsRoute(navigateToInvestmentDetail)
    }
}