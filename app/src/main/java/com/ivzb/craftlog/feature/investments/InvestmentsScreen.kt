package com.ivzb.craftlog.feature.investments

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
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.extenstion.toRelativeDateString
import com.ivzb.craftlog.feature.investments.InvestmentListItem.HeaderItem
import com.ivzb.craftlog.feature.investments.InvestmentListItem.InvestmentItem
import com.ivzb.craftlog.feature.investments.InvestmentListItem.OverviewItem
import com.ivzb.craftlog.feature.investments.viewmodel.InvestmentsViewModel
import com.ivzb.craftlog.ui.components.ListHeader
import com.ivzb.craftlog.ui.components.ExpandableSearchView
import com.ivzb.craftlog.util.trim
import java.math.BigDecimal

@Composable
fun InvestmentsRoute(
    navigateToInvestmentDetail: (Investment) -> Unit,
    navigateBack: () -> Unit,
    viewModel: InvestmentsViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadInvestments()
    }

    InvestmentsScreen(viewModel, navigateToInvestmentDetail, navigateBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentsScreen(
    viewModel: InvestmentsViewModel,
    navigateToInvestmentDetail: (Investment) -> Unit,
    navigateBack: () -> Unit
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
                        placeholderText = stringResource(id = R.string.investment_placeholder),
                        titleText = stringResource(id = R.string.investments),
                        onSearch = {
                            if (searchQuery != it) {
                                searchQuery = it
                                viewModel.loadInvestments(searchQuery)
                            }
                        },
                        navigateBack = navigateBack
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
            InvestmentList(
                investments = viewModel.state.investments,
                searchQuery = searchQuery,
                onClearSearch = {
                    searchQuery = ""
                    viewModel.loadInvestments(searchQuery)
                },
                navigateToInvestmentDetail = navigateToInvestmentDetail,
                onEdit = { investment ->
                    // todo: navigate to edit investment screen
                },
                onDelete = { investment ->
                    viewModel.deleteInvestment(investment)
                },
            )
        }
    }
}

@Composable
fun InvestmentList(
    investments: List<Investment>,
    searchQuery: String,
    onClearSearch: () -> Unit,
    navigateToInvestmentDetail: (Investment) -> Unit,
    onEdit: (Investment) -> Unit,
    onDelete: (Investment) -> Unit,
) {
    val sortedInvestmentList = investments
        .sortedByDescending { it.date }
        .map { InvestmentItem(it) }
        .groupBy { it.investment.date.trim() }
        .flatMap { (time, investments) ->
            listOf(
                HeaderItem(
                    time,
                    investments.sumOf { it.investment.cost })
            ) + investments
        }

    if (sortedInvestmentList.isEmpty()) {
        InvestmentEmptyView(searchQuery, onClearSearch)
    } else {
        InvestmentLazyColumn(
            sortedInvestmentList,
            searchQuery,
            navigateToInvestmentDetail,
            onEdit,
            onDelete
        )
    }
}

@Composable
fun InvestmentLazyColumn(
    items: List<InvestmentListItem>,
    searchQuery: String,
    navigateToInvestmentDetail: (Investment) -> Unit,
    onEdit: (Investment) -> Unit,
    onDelete: (Investment) -> Unit,
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
                InvestmentListItem(it, navigateToInvestmentDetail, onEdit, onDelete)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.InvestmentListItem(
    it: InvestmentListItem,
    navigateToInvestmentDetail: (Investment) -> Unit,
    onEditInvestment: (Investment) -> Unit,
    onDeleteInvestment: (Investment) -> Unit,
) {
    when (it) {
        is OverviewItem -> {}

        is HeaderItem -> ListHeader(it.time.toRelativeDateString(), it.total)

        is InvestmentItem -> {
            InvestmentCard(
                modifier = Modifier.animateItemPlacement(),
                investment = it.investment,
                navigateToInvestmentDetail = { investment ->
                    navigateToInvestmentDetail(investment)
                },
                onEdit = { investment ->
                    onEditInvestment(investment)
                },
                onDelete = { investment ->
                    onDeleteInvestment(investment)
                },
            )
        }
    }
}

@Composable
fun InvestmentEmptyView(searchQuery: String = "", onClearSearch: (() -> Unit)? = null) {
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
                text = stringResource(id = R.string.no_investments_yet),
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

sealed class InvestmentListItem(val id: Long) {

    data class OverviewItem(
        val investments: List<Investment>,
        val isInvestmentListEmpty: Boolean
    ) : InvestmentListItem(-1)

    data class InvestmentItem(val investment: Investment, val offset: Long = 0L) : InvestmentListItem((investment.id ?: 0) + offset)

    data class HeaderItem(val time: Long, val total: BigDecimal) : InvestmentListItem(time)
}
