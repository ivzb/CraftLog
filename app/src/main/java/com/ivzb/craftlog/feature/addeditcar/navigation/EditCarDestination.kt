package com.ivzb.craftlog.feature.addeditcar.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Car
import com.ivzb.craftlog.feature.addeditcar.AddEditCarRoute
import com.ivzb.craftlog.feature.cars.CAR
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination
import com.ivzb.craftlog.navigation.getItem

object EditCarDestination : CraftLogNavigationDestination {

    override val route = "edit_car_route"

    override val destination = "edit_car_destination"

}

fun NavGraphBuilder.editCarGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {
    composable(route = EditCarDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        val car = navController.getItem<Car>(CAR)

        AddEditCarRoute(car, navController)
    }
}
