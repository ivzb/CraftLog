package com.ivzb.craftlog.feature.addinvestment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.analytics.AnalyticsHelper
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.feature.addinvestment.viewmodel.AddInvestmentState
import com.ivzb.craftlog.feature.addinvestment.viewmodel.AddInvestmentViewModel
import com.ivzb.craftlog.ui.components.CategoryDropdownMenu
import com.ivzb.craftlog.ui.components.DateTextField
import com.ivzb.craftlog.ui.components.SuggestionsDropdownMenu
import com.ivzb.craftlog.util.InvestmentCategory
import com.ivzb.craftlog.util.SnackbarUtil.showSnackbar
import java.math.BigDecimal
import java.util.Date

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AddInvestmentRoute(navigateBack = {})
}

@Composable
fun AddInvestmentRoute(
    navigateBack: () -> Unit,
    viewModel: AddInvestmentViewModel = hiltViewModel()
) {
    val analyticsHelper = AnalyticsHelper.getInstance(LocalContext.current)
    AddInvestmentScreen(navigateBack, viewModel, analyticsHelper)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInvestmentScreen(
    navigateBack: () -> Unit,
    viewModel: AddInvestmentViewModel,
    analyticsHelper: AnalyticsHelper,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var cost by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf(InvestmentCategory.entries.first()) }
    var date by rememberSaveable { mutableStateOf(Date()) }

    var suggestedInvestments by rememberSaveable { mutableStateOf(listOf<Investment>()) }
    val nameState = mutableStateOf(name)
    val categoryState = mutableStateOf(category)

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel
            .isInvestmentSaved
            .collect {
                navigateBack()
                analyticsHelper.logEvent(AnalyticsEvents.INVESTMENT_SAVED)
            }
    }

    LaunchedEffect(Unit) {
        viewModel
            .suggestedInvestments
            .collect {
                suggestedInvestments = it
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                navigationIcon = {
                    FloatingActionButton(
                        onClick = {
                            analyticsHelper.logEvent(AnalyticsEvents.ADD_EXPENSE_ON_BACK_CLICKED)
                            navigateBack()
                        },
                        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(id = R.string.add_investment),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(56.dp),
                onClick = {
                    validateInvestment(
                        name = name,
                        amount = amount.toBigDecimalOrNull(),
                        cost = cost.toBigDecimalOrNull(),
                        categoryId = category.id,
                        date = date,
                        onInvalidate = {
                            val invalidatedValue = context.getString(it)

                            showSnackbar(
                                context.getString(
                                    R.string.value_is_empty,
                                    invalidatedValue
                                )
                            )

                            val event = String.format(
                                AnalyticsEvents.ADD_INVESTMENT_VALUE_INVALIDATED,
                                invalidatedValue
                            )

                            analyticsHelper.logEvent(event)
                        },
                        onValidate = { investment ->
                            viewModel.addInvestment(AddInvestmentState(investment))
                            analyticsHelper.logEvent(AnalyticsEvents.ADD_INVESTMENT_ON_SAVE_CLICKED)
                        },
                        viewModel = viewModel
                    )
                },
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = stringResource(id = R.string.done),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = stringResource(id = R.string.name),
                style = MaterialTheme.typography.bodyLarge
            )

            SuggestionsDropdownMenu(
                textFieldState = nameState,
                stringResource(R.string.investment_placeholder),
                onValueChange = {
                    name = it
                    viewModel.suggestInvestments(it)
                },
                suggestions = suggestedInvestments,
                onSuggestionSelected = { selectedInvestment ->
                    name = selectedInvestment.name
                    category = selectedInvestment.category
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.amount),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    TextField(
                        value = amount,
                        onValueChange = { amount = it },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        ),
                        placeholder = { Text(text = stringResource(R.string.amount_placeholder)) },
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = stringResource(id = R.string.cost),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    TextField(
                        value = cost,
                        onValueChange = { cost = it },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        placeholder = { Text(text = stringResource(R.string.amount_placeholder)) },
                    )
                }
            }

            Spacer(modifier = Modifier.padding(4.dp))

            CategoryDropdownMenu(categoryState, InvestmentCategory.entries) { category = it }

            Spacer(modifier = Modifier.padding(4.dp))

            DateTextField { date = it }
        }
    }
}

private fun validateInvestment(
    name: String,
    amount: BigDecimal?,
    cost: BigDecimal?,
    categoryId: String,
    date: Date,
    onInvalidate: (Int) -> Unit,
    onValidate: (Investment) -> Unit,
    viewModel: AddInvestmentViewModel
) {
    if (name.isEmpty()) {
        onInvalidate(R.string.name)
        return
    }

    if (amount == null) {
        onInvalidate(R.string.amount)
        return
    }

    if (cost == null) {
        onInvalidate(R.string.cost)
        return
    }

    if (categoryId.isEmpty()) {
        onInvalidate(R.string.category)
        return
    }

    if (date.time < 1) {
        onInvalidate(R.string.date)
        return
    }

    val investment = viewModel.createInvestment(
        name,
        amount,
        cost,
        categoryId,
        date
    )

    onValidate(investment)
}