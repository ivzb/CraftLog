package com.ivzb.craftlog.feature.investments

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
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.investments.viewmodel.InvestmentsState
import com.ivzb.craftlog.feature.investments.viewmodel.InvestmentsViewModel

@Composable
fun InvestmentsRoute(
    navigateToInvestmentDetail: (Investment) -> Unit,
    viewModel: InvestmentsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    InvestmentsScreen(state, navigateToInvestmentDetail)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentsScreen(state: InvestmentsState, navigateToInvestmentDetail: (Investment) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(top = 16.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.investments),
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
            InvestmentList(state, navigateToInvestmentDetail)
        }
    }
}

@Composable
fun InvestmentList(state: InvestmentsState, navigateToInvestmentDetail: (Investment) -> Unit) {
    val filteredInvestmentList = state.investments
    val sortedInvestmentList = filteredInvestmentList
        .sortedByDescending { it.date }
        .map { InvestmentListItem.InvestmentItem(it) }

    if (sortedInvestmentList.isEmpty()) {
        EmptyView()
    } else {
        InvestmentLazyColumn(sortedInvestmentList, navigateToInvestmentDetail)
    }
}

@Composable
fun InvestmentLazyColumn(sortedInvestmentList: List<InvestmentListItem>, navigateToInvestmentDetail: (Investment) -> Unit) {
    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = sortedInvestmentList,
            itemContent = {
                when (it) {
                    is InvestmentListItem.OverviewItem -> { }
                    is InvestmentListItem.HeaderItem -> {
                        Text(
                            modifier = Modifier
                                .padding(4.dp, 12.dp, 8.dp, 0.dp)
                                .fillMaxWidth(),
                            text = it.headerText.uppercase(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    is InvestmentListItem.InvestmentItem -> {
                        InvestmentCard(
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

sealed class InvestmentListItem {

    data class OverviewItem(
        val investments: List<Investment>,
        val isInvestmentListEmpty: Boolean
    ) : InvestmentListItem()

    data class InvestmentItem(val investment: Investment) : InvestmentListItem()

    data class HeaderItem(val headerText: String) : InvestmentListItem()
}
