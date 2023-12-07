package com.ivzb.craftlog.feature.budgetdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.analytics.AnalyticsHelper
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.feature.budgetdetail.viewmodel.BudgetDetailViewModel
import com.ivzb.craftlog.util.SnackbarUtil.showSnackbar

@Composable
fun BudgetDetailRoute(
    budget: Budget?,
    onBackClicked: () -> Unit,
    viewModel: BudgetDetailViewModel = hiltViewModel()
) {
    budget?.let {
        BudgetDetailScreen(budget, viewModel, onBackClicked)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetDetailScreen(
    budget: Budget,
    viewModel: BudgetDetailViewModel,
    onBackClicked: () -> Unit
) {
    var income by rememberSaveable { mutableStateOf("") }
    var bankStart by rememberSaveable { mutableStateOf("") }
    var bankEnd by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val analyticsHelper = AnalyticsHelper.getInstance(context)

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                navigationIcon = {
                    FloatingActionButton(
                        onClick = {
                            analyticsHelper.logEvent(AnalyticsEvents.BUDGET_DETAIL_ON_BACK_CLICKED)
                            onBackClicked()
                        },
                        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                title = { }
            )
        },
        bottomBar = {
            Column {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(56.dp),
                    onClick = {
                        // todo: save data
                        analyticsHelper.logEvent(AnalyticsEvents.BUDGET_DETAIL_DONE_CLICKED)
                        showSnackbar(context.getString(R.string.your_budget_is_saved))
                        onBackClicked()
                    },
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Text(
                        text = stringResource(id = R.string.done),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {

            Text(
                text = stringResource(id = R.string.income),
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = income,
                onValueChange = { income = it },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                placeholder = { Text(text = stringResource(R.string.budget_amount_placeholder)) },
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = stringResource(id = R.string.bank_start),
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = bankStart,
                onValueChange = { bankStart = it },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                placeholder = { Text(text = stringResource(R.string.budget_amount_placeholder)) },
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = stringResource(id = R.string.bank_end),
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = bankEnd,
                onValueChange = { bankEnd = it },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                placeholder = { Text(text = stringResource(R.string.budget_amount_placeholder)) },
            )

            Spacer(modifier = Modifier.padding(4.dp))

        }
    }
}