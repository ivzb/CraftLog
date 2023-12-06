package com.ivzb.craftlog.feature.expenseconfirm.navigation

import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.expenseconfirm.ExpenseConfirmRoute
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

const val EXPENSE = "expense"

object ExpenseConfirmDestination : CraftLogNavigationDestination {

    override val route = "expense_confirm_route"
    override val destination = "expense_confirm_destination"

}

fun NavGraphBuilder.expenseConfirmGraph(
    navController: NavController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    onBackClicked: () -> Unit,
    navigateToHome: () -> Unit
) {

    composable(
        route = ExpenseConfirmDestination.route,
    ) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }
        val expenseBundle = navController.previousBackStackEntry?.savedStateHandle?.get<Bundle>(EXPENSE)
        val expense = expenseBundle?.getParcelable<Expense>(EXPENSE)
        ExpenseConfirmRoute(expense, onBackClicked, navigateToHome)
    }
}
