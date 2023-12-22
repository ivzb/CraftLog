package com.ivzb.craftlog.feature.expenses

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivzb.craftlog.R
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.expenses.viewmodel.ExpensesState
import com.ivzb.craftlog.feature.expenses.viewmodel.ExpensesViewModel
import com.ivzb.craftlog.feature.home.ExpenseCard
import com.ivzb.craftlog.feature.home.ExpenseListItem
import com.ivzb.craftlog.ui.components.ExpandableSearchView

@Composable
fun ExpensesRoute(
    navigateToExpenseDetail: (Expense) -> Unit,
    viewModel: ExpensesViewModel = hiltViewModel()
) {

    ExpensesScreen(viewModel, navigateToExpenseDetail)
}

// todo: add notes
// todo: add todo list
// todo: add future reminders

// todo: hide budget, expenses and investments and group them to a finance screen
// todo: all of these screens would still be accessible via "more" button

// todo: add more fields to mortgage expense (principal, interest and insurance)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(viewModel: ExpensesViewModel, navigateToExpenseDetail: (Expense) -> Unit) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(top = 16.dp),
                title = {
                    ExpandableSearchView(
                        searchText = searchQuery,
                        placeholderText = stringResource(id = R.string.expense_placeholder),
                        titleText = stringResource(id = R.string.expenses),
                        onSearch = {
                            if (searchQuery != it) {
                                searchQuery = it
                                viewModel.loadExpenses(searchQuery)
                            }
                        },
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
            ExpenseList(
                expenses = viewModel.state.expenses,
                searchQuery = searchQuery,
                navigateToExpenseDetail = navigateToExpenseDetail
            )
        }
    }
}

@Composable
fun ExpenseList(
    expenses: List<Expense>,
    searchQuery: String = "",
    limit: Int = -1,
    navigateToExpenseDetail: (Expense) -> Unit
) {
    val sortedExpenseList = expenses
        .sortedByDescending { it.date }
        .map { ExpenseListItem.ExpenseItem(it) }
        .take(if (limit == -1) expenses.size else limit)

    if (sortedExpenseList.isEmpty()) {
        EmptyView()
    } else {
        ExpenseLazyColumn(sortedExpenseList, searchQuery, navigateToExpenseDetail)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpenseLazyColumn(
    items: List<ExpenseListItem>,
    searchQuery: String,
    navigateToExpenseDetail: (Expense) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(items.firstOrNull()) {
        if (items.isNotEmpty() && searchQuery.isEmpty()) {
            lazyListState.animateScrollToItem(0)
        }
    }

    LazyColumn(
        modifier = Modifier,
        lazyListState,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = items,
            key = { it.id },
            itemContent = {
                when (it) {
                    is ExpenseListItem.OverviewItem -> { }
                    is ExpenseListItem.HeaderItem -> {
                        Text(
                            modifier = Modifier
                                .animateItemPlacement()
                                .padding(4.dp, 12.dp, 8.dp, 0.dp)
                                .fillMaxWidth(),
                            text = it.headerText.uppercase(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    is ExpenseListItem.ExpenseItem -> {
                        ExpenseCard(
                            modifier = Modifier.animateItemPlacement(),
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
