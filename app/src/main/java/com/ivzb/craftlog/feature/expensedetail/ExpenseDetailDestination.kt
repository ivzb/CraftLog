package com.ivzb.craftlog.feature.expensedetail

import android.os.Build
import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.expenses.EXPENSE
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination
import com.ivzb.craftlog.navigation.getItem
import com.ivzb.craftlog.util.getParcelable

object ExpenseDetailDestination : CraftLogNavigationDestination {

    override val route = "expense_detail_route"
    override val destination = "expense_detail_destination"

}

fun NavGraphBuilder.expenseDetailGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {

    composable(
        route = ExpenseDetailDestination.route,
    ) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        val expense = navController.getItem<Expense>(EXPENSE)

        ExpenseDetailRoute(expense, navController)
    }
}
