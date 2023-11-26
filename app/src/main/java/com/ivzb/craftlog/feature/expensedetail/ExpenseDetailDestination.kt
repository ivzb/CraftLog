package com.ivzb.craftlog.feature.expensedetail

import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.expenses.EXPENSE
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object ExpenseDetailDestination : CraftLogNavigationDestination {

    override val route = "expense_detail_route"
    override val destination = "expense_detail_destination"

}

fun NavGraphBuilder.expenseDetailGraph(navController: NavController, bottomBarVisibility: MutableState<Boolean>, fabVisibility: MutableState<Boolean>, onBackClicked: () -> Unit) {

    composable(
        route = ExpenseDetailDestination.route,
    ) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabVisibility.value = false
        }
        val expenseBundle = navController.previousBackStackEntry?.savedStateHandle?.get<Bundle>(
            EXPENSE
        )
        val expense = expenseBundle?.getParcelable<Expense>(EXPENSE)
        ExpenseDetailRoute(expense, onBackClicked)
    }
}
