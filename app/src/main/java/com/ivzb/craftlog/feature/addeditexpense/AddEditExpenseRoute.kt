package com.ivzb.craftlog.feature.addeditexpense

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
import com.ivzb.craftlog.feature.addeditexpense.viewmodel.AddEditExpenseState
import com.ivzb.craftlog.feature.addeditexpense.viewmodel.AddEditExpenseViewModel
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
    AddEditExpenseRoute(null, navigateBack = {})
}

@Composable
fun AddEditExpenseRoute(
    expense: Expense?,
    navigateBack: () -> Unit,
    viewModel: AddEditExpenseViewModel = hiltViewModel()
) {
    val analyticsHelper = AnalyticsHelper.getInstance(LocalContext.current)
    AddEditExpenseScreen(expense, navigateBack, viewModel, analyticsHelper)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpenseScreen(
    expense: Expense?,
    navigateBack: () -> Unit,
    viewModel: AddEditExpenseViewModel,
    analyticsHelper: AnalyticsHelper,
) {
    val id by rememberSaveable { mutableStateOf(expense?.id ?: 0L) }
    var name by rememberSaveable { mutableStateOf(expense?.name ?: "") }
    var amount by rememberSaveable { mutableStateOf(expense?.amount?.toPlainString() ?: "") }
    var category by rememberSaveable { mutableStateOf(expense?.category ?: ExpenseCategory.entries.first()) }
    var date by rememberSaveable { mutableStateOf(expense?.date ?: Date()) }
    val additionalData = remember { mutableStateMapOf<String, String>() }
    expense?.additionalData?.forEach { (key, value) -> additionalData[key] = value }

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
                            analyticsHelper.logEvent(AnalyticsEvents.ADD_EDIT_EXPENSE_ON_BACK_CLICKED)
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
                        text = stringResource(id = if (id == 0L) R.string.add_expense else R.string.edit_expense),
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
                        id = id,
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
                                AnalyticsEvents.ADD_EDIT_EXPENSE_VALUE_INVALIDATED,
                                invalidatedValue
                            )

                            analyticsHelper.logEvent(event)
                        },
                        onValidate = { expense ->
                            if (expense.id == 0L) {
                                viewModel.addExpense(AddEditExpenseState(expense))
                            } else {
                                viewModel.editExpense(AddEditExpenseState(expense))
                            }

                            analyticsHelper.logEvent(AnalyticsEvents.ADD_EDIT_EXPENSE_ON_SAVE_CLICKED)
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

            DateTextField(date) { date = it }

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
    id: Long,
    name: String,
    amount: BigDecimal?,
    categoryId: String,
    date: Date,
    additionalData: Map<String, String>,
    onInvalidate: (Int) -> Unit,
    onValidate: (Expense) -> Unit,
    viewModel: AddEditExpenseViewModel
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
        id,
        name,
        amount,
        categoryId,
        date,
        additionalData
    )

    onValidate(expense)
}