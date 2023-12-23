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
import com.ivzb.craftlog.feature.finance.viewmodel.FinanceState
import com.ivzb.craftlog.feature.finance.viewmodel.FinanceViewModel
import com.ivzb.craftlog.feature.home.ExpenseCard
import com.ivzb.craftlog.feature.investments.InvestmentCard

@Composable
fun FinanceRoute(
    navigateToBudgetDetail: (Budget) -> Unit,
    navigateToExpenseDetail: (Expense) -> Unit,
    navigateToInvestmentDetail: (Investment) -> Unit,
    viewModel: FinanceViewModel = hiltViewModel(),
) {

    FinanceScreen(
        viewModel.state,
        navigateToBudgetDetail,
        navigateToExpenseDetail,
        navigateToInvestmentDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(
    state: FinanceState,
    navigateToBudgetDetail: (Budget) -> Unit,
    navigateToExpenseDetail: (Expense) -> Unit,
    navigateToInvestmentDetail: (Investment) -> Unit,
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
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        style = MaterialTheme.typography.titleLarge,
                        text = stringResource(id = R.string.budget),
                        color = MaterialTheme.colorScheme.primary
                    )

                    // todo: more button
                }

                item {
                    BudgetCard(budgetOverview) { budget ->
                        navigateToBudgetDetail(budget)
                    }
                }
            }

            item {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(id = R.string.expenses),
                    color = MaterialTheme.colorScheme.primary
                )

                // todo: more button
            }

            items(
                items = state.expenses,
                itemContent = {
                    ExpenseCard(
                        expense = it,
                        navigateToExpenseDetail = { expense ->
                            navigateToExpenseDetail(expense)
                        }
                    )
                }
            )

            item {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(id = R.string.investments),
                    color = MaterialTheme.colorScheme.primary
                )

                // todo: more button
            }

            items(
                items = state.investments,
                itemContent = {
                    InvestmentCard(
                        investment = it,
                        navigateToInvestmentDetail = { investment ->
                            navigateToInvestmentDetail(investment)
                        }
                    )
                }
            )
        }
    }
}