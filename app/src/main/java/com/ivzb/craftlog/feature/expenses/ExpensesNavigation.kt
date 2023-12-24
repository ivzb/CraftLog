package com.ivzb.craftlog.feature.expenses

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.addexpense.navigation.AddExpenseDestination
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

const val EXPENSE = "expense"

object ExpensesDestination : CraftLogNavigationDestination {

    override val route = "expenses_route"

    override val destination = "expenses_destination"
}

fun NavGraphBuilder.expensesGraph(
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    navigateToExpenseDetail: (Expense) -> Unit
) {
    composable(route = ExpensesDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = FabBehaviour(
                visibility = true,
                textId = R.string.add_expense,
                icon = Icons.Default.Add,
                analyticsEvent = AnalyticsEvents.ADD_EXPENSE_CLICKED_FAB,
                destinationRoute = AddExpenseDestination.route,
            )
        }

        ExpensesRoute(navigateToExpenseDetail)
    }
}