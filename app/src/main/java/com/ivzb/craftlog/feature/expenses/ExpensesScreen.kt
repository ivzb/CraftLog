package com.ivzb.craftlog.feature.expenses

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivzb.craftlog.R
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.expenses.ExpenseListItem.ExpenseItem
import com.ivzb.craftlog.feature.expenses.ExpenseListItem.HeaderItem
import com.ivzb.craftlog.feature.expenses.ExpenseListItem.OverviewItem
import com.ivzb.craftlog.feature.expenses.viewmodel.ExpensesViewModel
import com.ivzb.craftlog.ui.components.ExpandableSearchView
import com.ivzb.craftlog.ui.components.ListHeader
import com.ivzb.craftlog.util.trim
import java.math.BigDecimal

@Composable
fun ExpensesRoute(
    navigateToExpenseDetail: (Expense) -> Unit,
    onBackClicked: () -> Unit,
    viewModel: ExpensesViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadExpenses()
    }

    ExpensesScreen(viewModel, navigateToExpenseDetail, onBackClicked)
}

// todo: add todo list as notes feature
// todo: add future reminders

// todo: when pasting to notes, show confirmation dialog on paste when field is not empty (or concatinate to current value)

// todo: allow user to see previous budgets with calendar view with months only

// todo: additional data to investments

// todo: add action menu to expenses and investments

// todo: allow budget partial validation (enter only one field at a time)

// todo: fix navigation after adding expense or investment via finance screen

// todo: cars - create, parts bought and mileage mounted, fuel consumption, reminders - technical review, insurance, vignette, etc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    viewModel: ExpensesViewModel,
    navigateToExpenseDetail: (Expense) -> Unit,
    onBackClicked: () -> Unit
) {
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
                        onBackClicked = onBackClicked
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
                onClearSearch = {
                    searchQuery = ""
                    viewModel.loadExpenses(searchQuery)
                },
                navigateToExpenseDetail = navigateToExpenseDetail
            )
        }
    }
}

@Composable
fun ExpenseList(
    expenses: List<Expense>,
    searchQuery: String,
    onClearSearch: () -> Unit,
    navigateToExpenseDetail: (Expense) -> Unit
) {
    val sortedExpenseList = expenses
        .sortedByDescending { it.date }
        .map { ExpenseItem(it) }
        .groupBy { it.expense.date.trim() }
        .flatMap { (time, expenses) -> listOf(HeaderItem(time, expenses.sumOf { it.expense.amount })) + expenses }

    if (sortedExpenseList.isEmpty()) {
        ExpenseEmptyView(searchQuery, onClearSearch)
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
                ExpenseListItem(it, navigateToExpenseDetail)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.ExpenseListItem(it: ExpenseListItem, navigateToExpenseDetail: (Expense) -> Unit) {
    when (it) {
        is OverviewItem -> {}

        is HeaderItem -> ListHeader(it.time, -it.total)

        is ExpenseItem -> {
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

@Composable
fun ExpenseEmptyView(searchQuery: String = "", onClearSearch: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (searchQuery.isEmpty()) {
            Text(
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineMedium,
                text = stringResource(id = R.string.no_expenses_yet),
                color = MaterialTheme.colorScheme.tertiary
            )

            return
        }

        Text(
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium,
            text = stringResource(id = R.string.no_results_found_for, searchQuery),
            color = MaterialTheme.colorScheme.tertiary
        )

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .height(56.dp),
            onClick = {
                onClearSearch?.invoke()
            },
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(
                text = stringResource(id = R.string.clear_search),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

sealed class ExpenseListItem(val id: Long) {

    data class OverviewItem(
        val expensesToday: List<Expense>,
        val isExpenseListEmpty: Boolean
    ) : ExpenseListItem(-1)

    data class ExpenseItem(val expense: Expense) : ExpenseListItem(expense.id ?: 0)

    data class HeaderItem(val time: Long, val total: BigDecimal) : ExpenseListItem(time)
}
