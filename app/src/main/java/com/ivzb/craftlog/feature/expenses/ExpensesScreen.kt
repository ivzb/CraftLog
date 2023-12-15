package com.ivzb.craftlog.feature.expenses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivzb.craftlog.R
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.home.ExpenseCard
import com.ivzb.craftlog.feature.home.ExpenseListItem
import com.ivzb.craftlog.feature.expenses.viewmodel.ExpensesState
import com.ivzb.craftlog.feature.expenses.viewmodel.ExpensesViewModel

@Composable
fun ExpensesRoute(
    navigateToExpenseDetail: (Expense) -> Unit,
    viewModel: ExpensesViewModel = hiltViewModel()
) {
    val state = viewModel.state
    ExpensesScreen(state, navigateToExpenseDetail)
}

// todo: add search
// todo: add notes
// todo: add todo list
// todo: add future reminders

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(state: ExpensesState, navigateToExpenseDetail: (Expense) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(top = 16.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.expenses),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall,
                    )
                }
            )
        },
        bottomBar = { },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExpenseList(state, navigateToExpenseDetail)
        }
    }
}

@Composable
fun ExpenseList(state: ExpensesState, navigateToExpenseDetail: (Expense) -> Unit) {
    val filteredExpenseList = state.expenses
    val sortedExpenseList = filteredExpenseList
        .sortedByDescending { it.date }
        .map { ExpenseListItem.ExpenseItem(it) }

    if (sortedExpenseList.isEmpty()) {
        EmptyView()
    } else {
        ExpenseLazyColumn(sortedExpenseList, navigateToExpenseDetail)
    }
}

@Composable
fun ExpenseLazyColumn(sortedExpenseList: List<ExpenseListItem>, navigateToExpenseDetail: (Expense) -> Unit) {
    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = sortedExpenseList,
            itemContent = {
                when (it) {
                    is ExpenseListItem.OverviewItem -> { }
                    is ExpenseListItem.HeaderItem -> {
                        Text(
                            modifier = Modifier
                                .padding(4.dp, 12.dp, 8.dp, 0.dp)
                                .fillMaxWidth(),
                            text = it.headerText.uppercase(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    is ExpenseListItem.ExpenseItem -> {
                        ExpenseCard(
                            expense = it.expense,
                            navigateToExpenseDetail = { expense ->
                                navigateToExpenseDetail(expense)
                            }
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium,
            text = stringResource(id = R.string.no_expenses_yet),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}
