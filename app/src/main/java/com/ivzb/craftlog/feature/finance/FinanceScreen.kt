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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivzb.craftlog.R
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.budget.BudgetCard
import com.ivzb.craftlog.feature.expenses.ExpenseEmptyView
import com.ivzb.craftlog.feature.expenses.ExpenseListItem
import com.ivzb.craftlog.feature.finance.viewmodel.FinanceState
import com.ivzb.craftlog.feature.finance.viewmodel.FinanceViewModel
import com.ivzb.craftlog.feature.investments.InvestmentEmptyView
import com.ivzb.craftlog.feature.investments.InvestmentListItem
import com.ivzb.craftlog.ui.components.CategoryTitleBar
import com.ivzb.craftlog.util.trim

@Composable
fun FinanceRoute(
    navigateToBudget: () -> Unit,
    navigateToBudgetDetail: (Budget) -> Unit,
    navigateToExpenses: () -> Unit,
    navigateToExpenseDetail: (Expense) -> Unit,
    navigateToAddExpense: () -> Unit,
    navigateToInvestments: () -> Unit,
    navigateToInvestmentDetail: (Investment) -> Unit,
    navigateToAddInvestment: () -> Unit,
    viewModel: FinanceViewModel = hiltViewModel(),
) {

    FinanceScreen(
        viewModel.state,
        navigateToBudget,
        navigateToBudgetDetail,
        navigateToExpenses,
        navigateToExpenseDetail,
        navigateToAddExpense,
        navigateToInvestments,
        navigateToInvestmentDetail,
        navigateToAddInvestment
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(
    state: FinanceState,
    navigateToBudget: () -> Unit,
    navigateToBudgetDetail: (Budget) -> Unit,
    navigateToExpenses: () -> Unit,
    navigateToExpenseDetail: (Expense) -> Unit,
    navigateToAddExpense: () -> Unit,
    navigateToInvestments: () -> Unit,
    navigateToInvestmentDetail: (Investment) -> Unit,
    navigateToAddInvestment: () -> Unit,
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
            state.budgetOverview?.let { budgetOverview ->
                item {
                    CategoryTitleBar(
                        title = stringResource(id = R.string.budget),
                        showAddButton = false,
                        onMoreClick = {
                            navigateToBudget()
                        }
                    )
                }

                item {
                    BudgetCard(budgetOverview) { budget ->
                        navigateToBudgetDetail(budget)
                    }
                }
            }

            item {
                CategoryTitleBar(
                    title = stringResource(id = R.string.expenses),
                    onAddClick = {
                        navigateToAddExpense()
                    },
                    onMoreClick = {
                        navigateToExpenses()
                    }
                )
            }

            val expenses = state.expenses
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
                    ExpenseListItem(it, navigateToExpenseDetail = navigateToExpenseDetail)
                }
            )

            item {
                CategoryTitleBar(
                    title = stringResource(id = R.string.investments),
                    onAddClick = {
                        navigateToAddInvestment()
                    },
                    onMoreClick = {
                        navigateToInvestments()
                    })
            }

            val investments = state.investments
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
                    InvestmentListItem(it, navigateToInvestmentDetail)
                }
            )
        }
    }
}