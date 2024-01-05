package com.ivzb.craftlog.feature.addeditexpense.navigation

import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.addeditexpense.AddEditExpenseRoute
import com.ivzb.craftlog.feature.expenses.EXPENSE
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination
import com.ivzb.craftlog.util.getParcelable

object EditExpenseDestination : CraftLogNavigationDestination {

    override val route = "edit_expense_route"

    override val destination = "edit_expense_destination"

}

fun NavGraphBuilder.editExpenseGraph(
    navController: NavController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    navigateBack: () -> Unit,
) {
    composable(route = EditExpenseDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        val expenseBundle = navController.previousBackStackEntry?.savedStateHandle?.get<Bundle>(EXPENSE)
        val expense = getParcelable<Expense>(expenseBundle, EXPENSE)

        AddEditExpenseRoute(expense, navigateBack)
    }
}
