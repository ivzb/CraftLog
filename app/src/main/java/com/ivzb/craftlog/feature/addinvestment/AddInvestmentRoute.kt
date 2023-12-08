package com.ivzb.craftlog.feature.addinvestment

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.ivzb.craftlog.feature.addexpense.viewmodel.AddExpenseState
import com.ivzb.craftlog.feature.addinvestment.viewmodel.AddInvestmentState
import com.ivzb.craftlog.feature.addinvestment.viewmodel.AddInvestmentViewModel
import com.ivzb.craftlog.util.SnackbarUtil.showSnackbar
import java.math.BigDecimal
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AddInvestmentRoute(onBackClicked = {}, navigateToHome = {})
}

@Composable
fun AddInvestmentRoute(
    onBackClicked: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: AddInvestmentViewModel = hiltViewModel()
) {
    val analyticsHelper = AnalyticsHelper.getInstance(LocalContext.current)
    AddInvestmentScreen(onBackClicked, navigateToHome, viewModel, analyticsHelper)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInvestmentScreen(
    onBackClicked: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: AddInvestmentViewModel,
    analyticsHelper: AnalyticsHelper,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var cost by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf(Date()) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel
            .isInvestmentSaved
            .collect {
                navigateToHome()
                showSnackbar(context.getString(R.string.your_investment_is_saved))
                analyticsHelper.logEvent(AnalyticsEvents.INVESTMENT_SAVED)
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
                            onBackClicked()
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
                        modifier = Modifier.padding(16.dp),
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
                            viewModel.addInvestment(context, AddInvestmentState(investment))
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

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                placeholder = { Text(text = stringResource(R.string.investment_placeholder)) },
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

            DateTextField { date = it }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTextField(onDateSelected: (Date) -> Unit) {
    Text(
        text = stringResource(id = R.string.date),
        style = MaterialTheme.typography.bodyLarge
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    val currentDate = Date().toFormattedString()
    var selectedDate by rememberSaveable { mutableStateOf(currentDate) }

    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog =
        DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val newDate = Calendar.getInstance()
            newDate.set(year, month, dayOfMonth)
            selectedDate = "${month.toMonthName()} $dayOfMonth, $year"
            onDateSelected(newDate.timeInMillis.toDate())
        }, year, month, day)

    TextField(
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        value = selectedDate,
        onValueChange = {},
        trailingIcon = { Icons.Default.DateRange },
        interactionSource = interactionSource
    )

    if (isPressed) {
        datePickerDialog.show()
    }
}

fun Long.toDate(): Date {
    return Date(this)
}

fun Int.toMonthName(): String {
    return DateFormatSymbols().months[this]
}

fun Date.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("dd LLLL yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}

private fun validateInvestment(
    name: String,
    amount: BigDecimal?,
    cost: BigDecimal?,
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

    if (date.time < 1) {
        onInvalidate(R.string.date)
        return
    }

    val investment = viewModel.createInvestment(
        name,
        amount,
        cost,
        date
    )

    onValidate(investment)
}