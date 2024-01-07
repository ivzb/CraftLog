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
import androidx.navigation.NavHostController
import com.ivzb.craftlog.R
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.extenstion.toRelativeDateString
import com.ivzb.craftlog.feature.expenses.ExpenseListItem.ExpenseItem
import com.ivzb.craftlog.feature.expenses.ExpenseListItem.HeaderItem
import com.ivzb.craftlog.feature.expenses.ExpenseListItem.OverviewItem
import com.ivzb.craftlog.feature.expenses.viewmodel.ExpensesViewModel
import com.ivzb.craftlog.navigation.navigateBack
import com.ivzb.craftlog.navigation.navigateToEditExpense
import com.ivzb.craftlog.navigation.navigateToExpenseDetail
import com.ivzb.craftlog.ui.components.ExpandableSearchView
import com.ivzb.craftlog.ui.components.ListHeader
import com.ivzb.craftlog.util.trim
import java.math.BigDecimal

@Composable
fun ExpensesRoute(
    navController: NavHostController,
    viewModel: ExpensesViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadExpenses()
    }

    ExpensesScreen(navController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    navController: NavHostController,
    viewModel: ExpensesViewModel,
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
                        navigateBack = {
                            navController.navigateBack()
                        }
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
                onExpenseDetail = { expense ->
                    navController.navigateToExpenseDetail(expense)
                },
                onEdit = { expense ->
                    navController.navigateToEditExpense(expense)
                },
                onDelete = { expense ->
                    viewModel.deleteExpense(expense)
                }
            )
        }
    }
}

@Composable
fun ExpenseList(
    expenses: List<Expense>,
    searchQuery: String,
    onClearSearch: () -> Unit,
    onExpenseDetail: (Expense) -> Unit,
    onEdit: (Expense) -> Unit,
    onDelete: (Expense) -> Unit
) {
    val sortedExpenseList = expenses
        .sortedByDescending { it.date }
        .map { ExpenseItem(it) }
        .groupBy { it.expense.date.trim() }
        .flatMap { (time, expenses) ->
            listOf(
                HeaderItem(
                    time,
                    expenses.sumOf { it.expense.amount })
            ) + expenses
        }

    if (sortedExpenseList.isEmpty()) {
        ExpenseEmptyView(searchQuery, onClearSearch)
    } else {
        ExpenseLazyColumn(sortedExpenseList, searchQuery, onExpenseDetail, onEdit, onDelete)
    }
}

@Composable
fun ExpenseLazyColumn(
    items: List<ExpenseListItem>,
    searchQuery: String,
    onExpenseDetail: (Expense) -> Unit,
    onEdit: (Expense) -> Unit,
    onDelete: (Expense) -> Unit
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
                ExpenseListItem(it, onExpenseDetail, onEdit, onDelete)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.ExpenseListItem(
    it: ExpenseListItem,
    onExpenseDetail: (Expense) -> Unit,
    onEditExpense: (Expense) -> Unit,
    onDeleteExpense: (Expense) -> Unit
) {
    when (it) {
        is OverviewItem -> {}

        is HeaderItem -> ListHeader(it.time.toRelativeDateString(), -it.total)

        is ExpenseItem -> {
            ExpenseCard(
                modifier = Modifier.animateItemPlacement(),
                expense = it.expense,
                navigateToExpenseDetail = { expense ->
                    onExpenseDetail(expense)
                },
                onEdit = { expense ->
                    onEditExpense(expense)
                },
                onDelete = { expense ->
                    onDeleteExpense(expense)
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

    data class ExpenseItem(val expense: Expense, val offset: Long = 0) :
        ExpenseListItem((expense.id ?: 0) + offset)

    data class HeaderItem(val time: Long, val total: BigDecimal) : ExpenseListItem(time)
}
