package com.ivzb.craftlog.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.addeditexpense.navigation.AddExpenseDestination
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object HomeDestination : CraftLogNavigationDestination {

    override val route = "home_route"

    override val destination = "home_destination"

}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {
    composable(route = HomeDestination.route) {
        LaunchedEffect(Unit) {
            bottomBarVisibility.value = true
            fabBehaviour.value = FabBehaviour(
                visibility = true,
                textId = R.string.add_expense,
                icon = Icons.Default.Add,
                analyticsEvent = AnalyticsEvents.ADD_EDIT_EXPENSE_CLICKED_FAB,
                destinationRoute = AddExpenseDestination.route,
            )
        }

        HomeRoute(navController)
    }
}