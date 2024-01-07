package com.ivzb.craftlog.feature.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ivzb.craftlog.R
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.domain.model.BudgetOverview
import com.ivzb.craftlog.feature.budget.viewmodel.BudgetOverviewState
import com.ivzb.craftlog.feature.budget.viewmodel.BudgetViewModel
import com.ivzb.craftlog.navigation.navigateBack
import com.ivzb.craftlog.navigation.navigateToBudgetDetail

@Composable
fun BudgetRoute(
    navController: NavHostController,
    viewModel: BudgetViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadBudget()
    }

    val state = viewModel.state
    BudgetScreen(navController, state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    navController: NavHostController,
    state: BudgetOverviewState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(top = 16.dp),
                navigationIcon = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigateBack()
                        },
                        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.padding(8.dp),
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
            BudgetList(
                state.budgetOverview,
                navigateToBudgetDetail = { budget ->
                    navController.navigateToBudgetDetail(budget)
                }
            )
        }
    }
}

@Composable
fun BudgetList(
    budgetOverview: BudgetOverview?,
    navigateToBudgetDetail: (Budget) -> Unit
) {
    budgetOverview?.let {
        BudgetCard(
            budgetOverview = it
        ) { budget ->
            navigateToBudgetDetail(budget)
        }
    }
}
