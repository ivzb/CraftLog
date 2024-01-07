package com.ivzb.craftlog.feature.addeditexpense.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.addeditexpense.AddEditExpenseRoute
import com.ivzb.craftlog.feature.expenses.EXPENSE
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination
import com.ivzb.craftlog.navigation.getItem

object EditExpenseDestination : CraftLogNavigationDestination {

    override val route = "edit_expense_route"

    override val destination = "edit_expense_destination"

}

fun NavGraphBuilder.editExpenseGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {
    composable(route = EditExpenseDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        val expense = navController.getItem<Expense>(EXPENSE)

        AddEditExpenseRoute(expense, navController)
    }
}
