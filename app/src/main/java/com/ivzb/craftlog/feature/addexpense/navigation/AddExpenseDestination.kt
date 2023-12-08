package com.ivzb.craftlog.feature.addexpense.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.feature.addexpense.AddExpenseRoute
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object AddExpenseDestination : CraftLogNavigationDestination {

    override val route = "add_expense_route"
    override val destination = "add_expense_destination"

}

fun NavGraphBuilder.addExpenseGraph(
    navController: NavController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    onBackClicked: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable(route = AddExpenseDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        AddExpenseRoute(onBackClicked, navigateToHome)
    }
}
