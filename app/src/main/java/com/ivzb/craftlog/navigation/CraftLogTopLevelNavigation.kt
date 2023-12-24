package com.ivzb.craftlog.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ivzb.craftlog.R
import com.ivzb.craftlog.feature.budget.BudgetDestination
import com.ivzb.craftlog.feature.expenses.ExpensesDestination
import com.ivzb.craftlog.feature.finance.FinanceDestination
import com.ivzb.craftlog.feature.home.HomeDestination
import com.ivzb.craftlog.feature.investments.InvestmentsDestination
import com.ivzb.craftlog.feature.notes.NotesDestination

class CraftLogTopLevelNavigation (private val navController: NavHostController) {

    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}

data class TopLevelDestination(
    val route: String,
    val selectedIconId: Int,
    val unselectedIconId: Int,
    val iconTextId: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = HomeDestination.route,
        selectedIconId = R.drawable.ic_home_filled,
        unselectedIconId = R.drawable.ic_home_outlined,
        iconTextId = R.string.home
    ),

    TopLevelDestination(
        route = FinanceDestination.route,
        selectedIconId = R.drawable.ic_expenses_filled,
        unselectedIconId = R.drawable.ic_expenses_outlined,
        iconTextId = R.string.finance
    ),

    TopLevelDestination(
        route = NotesDestination.route,
        selectedIconId = R.drawable.ic_notes,
        unselectedIconId = R.drawable.ic_notes,
        iconTextId = R.string.notes
    ),

)