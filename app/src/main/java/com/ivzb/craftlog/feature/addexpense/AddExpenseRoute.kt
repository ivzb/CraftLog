package com.ivzb.craftlog.feature.addexpense

import android.app.DatePickerDialog
import android.content.Context
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivzb.craftlog.ExpenseCategory
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.analytics.AnalyticsHelper
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.extenstion.toFormattedDateString
import com.ivzb.craftlog.feature.addexpense.model.CalendarInformation
import com.ivzb.craftlog.feature.addexpense.viewmodel.AddExpenseViewModel
import com.ivzb.craftlog.getExpenseCategoryList
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
    AddExpenseRoute(onBackClicked = {}, navigateToExpenseConfirm = {})
}

@Composable
fun AddExpenseRoute(
    onBackClicked: () -> Unit,
    navigateToExpenseConfirm: (Expense) -> Unit,
    viewModel: AddExpenseViewModel = hiltViewModel()
) {
    val analyticsHelper = AnalyticsHelper.getInstance(LocalContext.current)
    AddExpenseScreen(onBackClicked, viewModel, analyticsHelper, navigateToExpenseConfirm)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onBackClicked: () -> Unit,
    viewModel: AddExpenseViewModel,
    analyticsHelper: AnalyticsHelper,
    navigateToExpenseConfirm: (Expense) -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf(ExpenseCategory.None.name) }
    var date by rememberSaveable { mutableStateOf(Date()) }

    val context = LocalContext.current

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
                        category = category,
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
                                AnalyticsEvents.ADD_EXPENSE_EXPENSE_VALUE_INVALIDATED,
                                invalidatedValue
                            )

                            analyticsHelper.logEvent(event)
                        },
                        onValidate = {
                            navigateToExpenseConfirm(it)
                            analyticsHelper.logEvent(AnalyticsEvents.ADD_EXPENSE_NAVIGATING_TO_EXPENSE_CONFIRM)
                        },
                        viewModel = viewModel
                    )
                },
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = stringResource(id = R.string.next),
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
                placeholder = { Text(text = stringResource(R.string.expense_placeholder)) },
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.amount),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    TextField(
                        modifier = Modifier.width(128.dp),
                        value = amount,
                        onValueChange = { amount = it },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        placeholder = { Text(text = stringResource(R.string.amount_placeholder)) },
                    )
                }

                CategoryDropdownMenu { category = it }
            }

            Spacer(modifier = Modifier.padding(4.dp))

            DateTextField { date = it }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdownMenu(onCategorySelected: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.category),
            style = MaterialTheme.typography.bodyLarge
        )

        val options = getExpenseCategoryList().map { it.name }
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(options[0]) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            onCategorySelected(selectionOption)
                            expanded = false
                        }
                    )
                }
            }
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

private fun validateExpense(
    name: String,
    amount: BigDecimal?,
    category: String,
    date: Date,
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

    if (category.isEmpty()) {
        onInvalidate(R.string.category)
        return
    }

    if (date.time < 1) {
        onInvalidate(R.string.date)
        return
    }

    val expense = viewModel.createExpense(
        name,
        amount,
        category,
        date
    )

    onValidate(expense)
}