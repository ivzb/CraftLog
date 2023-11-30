package com.ivzb.craftlog.feature.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivzb.craftlog.R
import com.ivzb.craftlog.feature.budget.viewmodel.BudgetState
import com.ivzb.craftlog.feature.budget.viewmodel.BudgetViewModel

@Composable
fun BudgetRoute(
    viewModel: BudgetViewModel = hiltViewModel()
) {
    val state = viewModel.state
    BudgetScreen(state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(state: BudgetState) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(top = 16.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.budget),
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
            BudgetList(state)
        }
    }
}

@Composable
fun BudgetList(state: BudgetState) {
    val budget = state.budget
//    BudgetLazyColumn(budget)

    Text(
        modifier = Modifier
            .padding(4.dp, 12.dp, 8.dp, 0.dp)
            .fillMaxWidth(),
        text = "budget here",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium,
    )
}

//@Composable
//fun BudgetLazyColumn(sortedBudgetList: List<BudgetListItem>) {
//    LazyColumn(
//        modifier = Modifier,
//        contentPadding = PaddingValues(vertical = 8.dp)
//    ) {
//        items(
//            items = sortedBudgetList,
//            itemContent = {
//                when (it) {
//                    is BudgetListItem.OverviewItem -> { }
//                    is BudgetListItem.HeaderItem -> {
//                        Text(
//                            modifier = Modifier
//                                .padding(4.dp, 12.dp, 8.dp, 0.dp)
//                                .fillMaxWidth(),
//                            text = it.headerText.uppercase(),
//                            textAlign = TextAlign.Center,
//                            style = MaterialTheme.typography.titleMedium,
//                        )
//                    }
//                    is BudgetListItem.BudgetItem -> {
//                        BudgetCard(
//                            expense = it.expense
//                        )
//                    }
//                }
//            }
//        )
//    }
//}
