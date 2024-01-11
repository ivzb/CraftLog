package com.ivzb.craftlog.feature.addeditcar.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.feature.addeditcar.AddEditCarRoute
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object AddCarDestination : CraftLogNavigationDestination {

    override val route = "add_car_route"

    override val destination = "add_car_destination"

}

fun NavGraphBuilder.addCarGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {
    composable(route = AddCarDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        AddEditCarRoute(null, navController)
    }
}
