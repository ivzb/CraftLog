package com.ivzb.craftlog.feature.addexpense

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
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
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.feature.addexpense.viewmodel.AddExpenseState
import com.ivzb.craftlog.feature.addexpense.viewmodel.AddExpenseViewModel
import com.ivzb.craftlog.ui.components.CategoryDropdownMenu
import com.ivzb.craftlog.ui.components.DateTextField
import com.ivzb.craftlog.ui.components.SuggestionsDropdownMenu
import com.ivzb.craftlog.util.ExpenseCategory
import com.ivzb.craftlog.util.SnackbarUtil.showSnackbar
import java.math.BigDecimal
import java.util.Date

const val PRINCIPAL = "principal"
const val INTEREST = "interest"
const val INSURANCE = "insurance"

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AddExpenseRoute(navigateBack = {})
}

@Composable
fun AddExpenseRoute(
    navigateBack: () -> Unit,
    viewModel: AddExpenseViewModel = hiltViewModel()
) {
    val analyticsHelper = AnalyticsHelper.getInstance(LocalContext.current)
    AddExpenseScreen(navigateBack, viewModel, analyticsHelper)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    navigateBack: () -> Unit,
    viewModel: AddExpenseViewModel,
    analyticsHelper: AnalyticsHelper,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf(ExpenseCategory.entries.first()) }
    var date by rememberSaveable { mutableStateOf(Date()) }
    val additionalData = remember { mutableStateMapOf<String, String>() }

    val hasAdditionalFields = category in listOf(ExpenseCategory.Mortgage)

    var suggestedExpenses by rememberSaveable { mutableStateOf(listOf<Expense>()) }
    val nameState = mutableStateOf(name)
    val categoryState = mutableStateOf(category)

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel
            .isExpenseSaved
            .collect {
                navigateBack()
                analyticsHelper.logEvent(AnalyticsEvents.EXPENSE_SAVED)
            }
    }

    LaunchedEffect(Unit) {
        viewModel
            .suggestedExpenses
            .collect {
                suggestedExpenses = it
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(id = R.string.add_expense),
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
                    validateExpense(
                        name = name,
                        amount = amount.toBigDecimalOrNull(),
                        categoryId = category.id,
                        date = date,
                        additionalData = additionalData.toMap(),
                        onInvalidate = {
                            val invalidatedValue = context.getString(it)

                            showSnackbar(
                                context.getString(
                                    R.string.value_is_empty,
                                    invalidatedValue
                                )
                            )

                            val event = String.format(
                                AnalyticsEvents.ADD_EXPENSE_VALUE_INVALIDATED,
                                invalidatedValue
                            )

                            analyticsHelper.logEvent(event)
                        },
                        onValidate = { expense ->
                            viewModel.addExpense(AddExpenseState(expense))
                            analyticsHelper.logEvent(AnalyticsEvents.ADD_EXPENSE_ON_SAVE_CLICKED)
                        },
                        viewModel = viewModel
                    )
                },
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = stringResource(id = R.string.save),
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
                placeholderText = stringResource(R.string.expense_placeholder),
                onValueChange = {
                    name = it
                    viewModel.suggestExpenses(it)
                },
                suggestions = suggestedExpenses,
                onSuggestionSelected = { selectedExpense ->
                    name = selectedExpense.name
                    category = selectedExpense.category
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
                            imeAction = if (hasAdditionalFields) ImeAction.Next else ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        placeholder = { Text(text = stringResource(R.string.amount_placeholder)) },
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    CategoryDropdownMenu(categoryState, ExpenseCategory.entries) { category = it }
                }
            }

            Spacer(modifier = Modifier.padding(4.dp))

            DateTextField { date = it }

            if (hasAdditionalFields) {
                when {
                    category == ExpenseCategory.Mortgage -> MortgageFields(additionalData)
                }
            }
        }
    }
}

@Composable
private fun MortgageFields(additionalData: SnapshotStateMap<String, String>) {
    Spacer(modifier = Modifier.padding(4.dp))

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.principal),
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                value = additionalData[PRINCIPAL].orEmpty(),
                onValueChange = { additionalData[PRINCIPAL] = it },
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
                text = stringResource(id = R.string.interest),
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                value = additionalData[INTEREST].orEmpty(),
                onValueChange = { additionalData[INTEREST] = it },
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
                text = stringResource(id = R.string.insurance),
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                value = additionalData[INSURANCE].orEmpty(),
                onValueChange = { additionalData[INSURANCE] = it },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                placeholder = { Text(text = stringResource(R.string.amount_placeholder)) },
            )
        }
    }
}

private fun validateExpense(
    name: String,
    amount: BigDecimal?,
    categoryId: String,
    date: Date,
    additionalData: Map<String, String>,
    onInvalidate: (Int) -> Unit,
    onValidate: (Expense) -> Unit,
    viewModel: AddExpenseViewModel
) {
    if (name.isEmpty()) {
        onInvalidate(R.string.name)
        return
    }

    if (amount == null) {
        onInvalidate(R.string.amount)
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

    if (categoryId == ExpenseCategory.Mortgage.id) {
        if (additionalData[PRINCIPAL].isNullOrEmpty()) {
            onInvalidate(R.string.principal)
            return
        }

        if (additionalData[INTEREST].isNullOrEmpty()) {
            onInvalidate(R.string.interest)
            return
        }

        if (additionalData[INSURANCE].isNullOrEmpty()) {
            onInvalidate(R.string.insurance)
            return
        }
    }

    val expense = viewModel.createExpense(
        name,
        amount,
        categoryId,
        date,
        additionalData
    )

    onValidate(expense)
}