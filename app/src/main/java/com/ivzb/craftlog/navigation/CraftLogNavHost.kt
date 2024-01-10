package com.ivzb.craftlog.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.feature.addeditexpense.navigation.addExpenseGraph
import com.ivzb.craftlog.feature.addeditexpense.navigation.editExpenseGraph
import com.ivzb.craftlog.feature.addeditinvestment.navigation.addInvestmentGraph
import com.ivzb.craftlog.feature.addeditinvestment.navigation.editInvestmentGraph
import com.ivzb.craftlog.feature.addeditnote.navigation.addNoteGraph
import com.ivzb.craftlog.feature.addeditnote.navigation.editNoteGraph
import com.ivzb.craftlog.feature.budget.budgetGraph
import com.ivzb.craftlog.feature.budgetdetail.budgetDetailGraph
import com.ivzb.craftlog.feature.cars.carsGraph
import com.ivzb.craftlog.feature.expensedetail.expenseDetailGraph
import com.ivzb.craftlog.feature.expenses.expensesGraph
import com.ivzb.craftlog.feature.finance.financeGraph
import com.ivzb.craftlog.feature.home.HomeDestination
import com.ivzb.craftlog.feature.home.homeGraph
import com.ivzb.craftlog.feature.investmentdetail.investmentDetailGraph
import com.ivzb.craftlog.feature.investments.investmentsGraph
import com.ivzb.craftlog.feature.notedetail.noteDetailGraph
import com.ivzb.craftlog.feature.notes.notesGraph

@Composable
fun CraftLogNavHost(
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        financeGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        expensesGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        expenseDetailGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        addExpenseGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        editExpenseGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        budgetGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        budgetDetailGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        investmentsGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        investmentDetailGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        addInvestmentGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        editInvestmentGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        notesGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        noteDetailGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        addNoteGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        editNoteGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )

        carsGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabBehaviour = fabBehaviour,
        )
    }
}