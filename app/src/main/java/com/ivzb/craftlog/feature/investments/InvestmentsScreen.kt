package com.ivzb.craftlog.feature.investments

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
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.investments.viewmodel.InvestmentsState
import com.ivzb.craftlog.feature.investments.viewmodel.InvestmentsViewModel
import com.ivzb.craftlog.ui.components.ExpandableSearchView

@Composable
fun InvestmentsRoute(
    navigateToInvestmentDetail: (Investment) -> Unit,
    viewModel: InvestmentsViewModel = hiltViewModel()
) {

    InvestmentsScreen(viewModel, navigateToInvestmentDetail)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentsScreen(
    viewModel: InvestmentsViewModel,
    navigateToInvestmentDetail: (Investment) -> Unit
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
                navigateToInvestmentDetail = navigateToInvestmentDetail
            )
        }
    }
}

@Composable
fun InvestmentList(
    investments: List<Investment>,
    searchQuery: String = "",
    limit: Int = -1,
    navigateToInvestmentDetail: (Investment) -> Unit
) {
    val sortedInvestmentList = investments
        .sortedByDescending { it.date }
        .map { InvestmentListItem.InvestmentItem(it) }
        .take(if (limit == -1) investments.size else limit)

    if (sortedInvestmentList.isEmpty()) {
        EmptyView()
    } else {
        InvestmentLazyColumn(sortedInvestmentList, searchQuery, navigateToInvestmentDetail)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InvestmentLazyColumn(
    items: List<InvestmentListItem>,
    searchQuery: String,
    navigateToInvestmentDetail: (Investment) -> Unit
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
                    is InvestmentListItem.OverviewItem -> {}
                    is InvestmentListItem.HeaderItem -> {
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

                    is InvestmentListItem.InvestmentItem -> {
                        InvestmentCard(
                            modifier = Modifier.animateItemPlacement(),
                            investment = it.investment,
                            navigateToInvestmentDetail = { investment ->
                                navigateToInvestmentDetail(investment)
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
            text = stringResource(id = R.string.no_investments_yet),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

sealed class InvestmentListItem(val id: Long) {

    data class OverviewItem(
        val investments: List<Investment>,
        val isInvestmentListEmpty: Boolean
    ) : InvestmentListItem(-2)

    data class InvestmentItem(val investment: Investment) : InvestmentListItem(investment.id ?: 0)

    data class HeaderItem(val headerText: String) : InvestmentListItem(-1)
}
