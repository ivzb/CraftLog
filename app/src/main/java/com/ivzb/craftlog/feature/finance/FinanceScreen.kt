package com.ivzb.craftlog.feature.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ivzb.craftlog.R
import com.ivzb.craftlog.feature.budget.BudgetCard
import com.ivzb.craftlog.feature.expenses.ExpenseEmptyView
import com.ivzb.craftlog.feature.expenses.ExpenseListItem
import com.ivzb.craftlog.feature.finance.viewmodel.FinanceViewModel
import com.ivzb.craftlog.feature.investments.InvestmentEmptyView
import com.ivzb.craftlog.feature.investments.InvestmentListItem
import com.ivzb.craftlog.navigation.navigateToAddExpense
import com.ivzb.craftlog.navigation.navigateToAddInvestment
import com.ivzb.craftlog.navigation.navigateToBudget
import com.ivzb.craftlog.navigation.navigateToBudgetDetail
import com.ivzb.craftlog.navigation.navigateToEditExpense
import com.ivzb.craftlog.navigation.navigateToEditInvestment
import com.ivzb.craftlog.navigation.navigateToExpenseDetail
import com.ivzb.craftlog.navigation.navigateToExpenses
import com.ivzb.craftlog.navigation.navigateToInvestmentDetail
import com.ivzb.craftlog.navigation.navigateToInvestments
import com.ivzb.craftlog.ui.components.CategoryTitleBar
import com.ivzb.craftlog.util.trim

@Composable
fun FinanceRoute(
    navController: NavHostController,
    viewModel: FinanceViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    FinanceScreen(
        navController,
        viewModel,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(
    navController: NavHostController,
    viewModel: FinanceViewModel,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(top = 16.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.finance),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall,
                    )
                }
            )
        },
        bottomBar = { },
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            viewModel.state.budgetOverview?.let { budgetOverview ->
                item {
                    CategoryTitleBar(
                        title = stringResource(id = R.string.budget),
                        showAddButton = false,
                        onMoreClick = {
                            navController.navigateToBudget()
                        }
                    )
                }

                item {
                    BudgetCard(budgetOverview) { budget ->
                        navController.navigateToBudgetDetail(budget)
                    }
                }
            }

            item {
                CategoryTitleBar(
                    title = stringResource(id = R.string.expenses),
                    onAddClick = {
                        navController.navigateToAddExpense()
                    },
                    onMoreClick = {
                        navController.navigateToExpenses()
                    }
                )
            }

            val expenses = viewModel.state.expenses
                .sortedByDescending { it.date }
                .map { ExpenseListItem.ExpenseItem(it) }
                .groupBy { it.expense.date.trim() }
                .flatMap { (time, expenses) ->
                    listOf(
                        ExpenseListItem.HeaderItem(
                            time,
                            expenses.sumOf { it.expense.amount })
                    ) + expenses
                }

            if (expenses.isEmpty()) {
                item {
                    ExpenseEmptyView()
                }
            }

            items(
                items = expenses,
                itemContent = {
                    ExpenseListItem(
                        it,
                        onExpenseDetail = { expense ->
                            navController.navigateToExpenseDetail(expense)
                        },
                        onEditExpense = { expense ->
                            navController.navigateToEditExpense(expense)
                        },
                        onDeleteExpense = { expense ->
                            viewModel.deleteExpense(expense)
                            viewModel.load()
                        }
                    )
                }
            )

            item {
                CategoryTitleBar(
                    title = stringResource(id = R.string.investments),
                    onAddClick = {
                        navController.navigateToAddInvestment()
                    },
                    onMoreClick = {
                        navController.navigateToInvestments()
                    })
            }

            val investments = viewModel.state.investments
                .sortedByDescending { it.date }
                .map { InvestmentListItem.InvestmentItem(it) }
                .groupBy { it.investment.date.trim() }
                .flatMap { (time, investments) ->
                    listOf(
                        InvestmentListItem.HeaderItem(
                            time,
                            investments.sumOf { it.investment.cost })
                    ) + investments
                }

            if (investments.isEmpty()) {
                item {
                    InvestmentEmptyView()
                }
            }

            items(
                items = investments,
                itemContent = {
                    InvestmentListItem(
                        it,
                        onInvestmentDetail = { investment ->
                            navController.navigateToInvestmentDetail(investment)
                        },
                        onEditInvestment = { investment ->
                            navController.navigateToEditInvestment(investment)
                        },
                        onDeleteInvestment = { investment ->
                            viewModel.deleteInvestment(investment)
                            viewModel.load()
                        },
                    )
                }
            )
        }
    }
}