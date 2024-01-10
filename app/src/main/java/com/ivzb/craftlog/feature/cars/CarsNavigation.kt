package com.ivzb.craftlog.feature.cars

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

const val CAR = "car"

object CarsDestination : CraftLogNavigationDestination {

    override val route = "cars_route"

    override val destination = "cars_destination"

}

fun NavGraphBuilder.carsGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {
    composable(route = CarsDestination.route) {
        LaunchedEffect(Unit) {
            bottomBarVisibility.value = true
            // todo
//            fabBehaviour.value = FabBehaviour(
//                visibility = true,
//                textId = R.string.add_car,
//                icon = Icons.Default.Add,
//                analyticsEvent = AnalyticsEvents.ADD_CAR_CLICKED_FAB,
//                destinationRoute = AddCarDestination.route,
//            )
        }

        CarsRoute(navController)
    }
}
