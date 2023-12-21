package com.ivzb.craftlog.feature.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ivzb.craftlog.analytics.AnalyticsHelper
import com.ivzb.craftlog.feature.finance.viewmodel.FinanceState
import com.ivzb.craftlog.feature.finance.viewmodel.FinanceViewModel

@Composable
fun FinanceRoute(
    navController: NavController,
    viewModel: FinanceViewModel = hiltViewModel()
) {
    val analyticsHelper = AnalyticsHelper.getInstance(LocalContext.current)
    val state = viewModel.state
    FinanceScreen(navController, analyticsHelper, state, viewModel)
}

@Composable
fun FinanceScreen(
    navController: NavController,
    analyticsHelper: AnalyticsHelper,
    state: FinanceState,
    viewModel: FinanceViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // todo

        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.titleSmall,
            text = "Finance",
            color = MaterialTheme.colorScheme.primary
        )
    }
}