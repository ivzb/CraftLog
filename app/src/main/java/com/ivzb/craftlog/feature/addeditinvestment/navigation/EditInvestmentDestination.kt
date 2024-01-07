package com.ivzb.craftlog.feature.addeditinvestment.navigation

import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.addeditinvestment.AddEditInvestmentRoute
import com.ivzb.craftlog.feature.investments.INVESTMENT
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination
import com.ivzb.craftlog.navigation.getItem
import com.ivzb.craftlog.util.getParcelable

object EditInvestmentDestination : CraftLogNavigationDestination {

    override val route = "edit_investment_route"

    override val destination = "edit_investment_destination"

}

fun NavGraphBuilder.editInvestmentGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {
    composable(route = EditInvestmentDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        val investment = navController.getItem<Investment>(INVESTMENT)

        AddEditInvestmentRoute(navController, investment)
    }
}
