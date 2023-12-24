package com.ivzb.craftlog.feature.finance

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object FinanceDestination : CraftLogNavigationDestination {

    override val route = "finance_route"

    override val destination = "finance_destination"

}

fun NavGraphBuilder.financeGraph(
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    navigateToBudget: () -> Unit,
    navigateToBudgetDetail: (Budget) -> Unit,
    navigateToExpenses: () -> Unit,
    navigateToExpenseDetail: (Expense) -> Unit,
    navigateToInvestments: () -> Unit,
    navigateToInvestmentDetail: (Investment) -> Unit,
) {
    composable(route = FinanceDestination.route) {
        LaunchedEffect(Unit) {
            bottomBarVisibility.value = true
            fabBehaviour.value = null
        }

        FinanceRoute(
            navigateToBudget,
            navigateToBudgetDetail,
            navigateToExpenses,
            navigateToExpenseDetail,
            navigateToInvestments,
            navigateToInvestmentDetail
        )
    }
}
