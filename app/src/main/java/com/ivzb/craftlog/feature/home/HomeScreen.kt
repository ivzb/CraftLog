package com.ivzb.craftlog.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.analytics.AnalyticsHelper
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.extenstion.toFormattedDateShortString
import com.ivzb.craftlog.extenstion.toFormattedDateString
import com.ivzb.craftlog.extenstion.toFormattedMonthDateString
import com.ivzb.craftlog.feature.addexpense.navigation.AddExpenseDestination
import com.ivzb.craftlog.feature.expenses.ExpenseListItem
import com.ivzb.craftlog.feature.home.data.CalendarDataSource
import com.ivzb.craftlog.feature.home.model.CalendarModel
import com.ivzb.craftlog.feature.home.viewmodel.HomeState
import com.ivzb.craftlog.feature.home.viewmodel.HomeViewModel
import com.ivzb.craftlog.feature.investments.InvestmentListItem
import com.ivzb.craftlog.feature.notes.NoteListItem
import com.ivzb.craftlog.ui.components.CategoryTitleBar
import java.util.Calendar
import java.util.Date

@Composable
fun HomeRoute(
    navigateToExpenses: () -> Unit,
    navigateToExpenseDetail: (Expense) -> Unit,
    navigateToAddExpense: () -> Unit,
    navigateToInvestments: () -> Unit,
    navigateToInvestmentDetail: (Investment) -> Unit,
    navigateToAddInvestment: () -> Unit,
    navigateToNotes: () -> Unit,
    navigateToNoteDetail: (Note) -> Unit,
    navigateToAddNote: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    val analyticsHelper = AnalyticsHelper.getInstance(LocalContext.current)
    val state = viewModel.state

    HomeScreen(
        analyticsHelper = analyticsHelper,
        state = state,
        navigateToExpenses = navigateToExpenses,
        navigateToExpenseDetail = navigateToExpenseDetail,
        navigateToAddExpense = navigateToAddExpense,
        onEditExpense = { expense ->
            // todo: navigate to edit expense screen
        },
        onDeleteExpense = { expense ->
            viewModel.deleteExpense(expense)
        },
        navigateToInvestments = navigateToInvestments,
        navigateToInvestmentDetail = navigateToInvestmentDetail,
        navigateToAddInvestment = navigateToAddInvestment,
        onEditInvestment = { investment ->
            // todo: navigate to edit investment screen
        },
        onDeleteInvestment = { investment ->
            viewModel.deleteInvestment(investment)
        },
        navigateToNotes = navigateToNotes,
        navigateToNoteDetail = navigateToNoteDetail,
        navigateToAddNote = navigateToAddNote,
        onEditNote = { note ->
            // todo: navigate to edit note screen
        },
        onDeleteNote = { note ->
            viewModel.deleteNote(note)
        }
    )
}

@Composable
fun HomeScreen(
    analyticsHelper: AnalyticsHelper,
    state: HomeState,
    navigateToExpenses: () -> Unit,
    navigateToExpenseDetail: (Expense) -> Unit,
    navigateToAddExpense: () -> Unit,
    onEditExpense: (Expense) -> Unit,
    onDeleteExpense: (Expense) -> Unit,
    navigateToInvestments: () -> Unit,
    navigateToInvestmentDetail: (Investment) -> Unit,
    navigateToAddInvestment: () -> Unit,
    onEditInvestment: (Investment) -> Unit,
    onDeleteInvestment: (Investment) -> Unit,
    navigateToNotes: () -> Unit,
    navigateToNoteDetail: (Note) -> Unit,
    navigateToAddNote: () -> Unit,
    onEditNote: (Note) -> Unit,
    onDeleteNote: (Note) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LastExpenses(
            analyticsHelper,
            state,
            navigateToExpenses,
            navigateToExpenseDetail,
            navigateToAddExpense,
            onEditExpense,
            onDeleteExpense,
            navigateToInvestments,
            navigateToInvestmentDetail,
            navigateToAddInvestment,
            onEditInvestment,
            onDeleteInvestment,
            navigateToNotes,
            navigateToNoteDetail,
            navigateToAddNote,
            onEditNote,
            onDeleteNote
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyOverviewCard(
    navController: NavController,
    analyticsHelper: AnalyticsHelper,
//    expensesToday: List<Expense>
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .height(156.dp),
        shape = RoundedCornerShape(36.dp),
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.tertiary
        ),
        onClick = {
            analyticsHelper.logEvent(AnalyticsEvents.ADD_EXPENSE_CLICKED_DAILY_OVERVIEW)
            navController.navigate(AddExpenseDestination.route)
        }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = "hello there",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                )

//                Text(
//                    text = stringResource(R.string.your_plan_for_today),
//                    fontWeight = FontWeight.Bold,
//                    style = MaterialTheme.typography.titleLarge,
//                )
//
//                Text(
//                    text = stringResource(
//                        id = R.string.daily_medicine_log,
//                        expensesToday.filter { it.expenseTaken }.size,
//                        expensesToday.size
//                    ),
//                    style = MaterialTheme.typography.titleSmall,
//                )
            }

//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.doctor), contentDescription = ""
//                )
//            }
        }
    }
}

@Composable
fun EmptyCard(
    analyticsHelper: AnalyticsHelper,
    selectedDateString: String,
) {
    analyticsHelper.logEvent(AnalyticsEvents.EMPTY_CARD_SHOWN)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(36.dp),
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.tertiary
        ),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                Text(
                    text = stringResource(
                        R.string.home_screen_empty_card_title,
                        selectedDateString
                    ),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = stringResource(
                        R.string.home_screen_empty_card_message,
                        selectedDateString
                    ),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

@Composable
fun LastExpenses(
    analyticsHelper: AnalyticsHelper,
    state: HomeState,
    navigateToExpenses: () -> Unit,
    navigateToExpenseDetail: (Expense) -> Unit,
    navigateToAddExpense: () -> Unit,
    onEditExpense: (Expense) -> Unit,
    onDeleteExpense: (Expense) -> Unit,
    navigateToInvestments: () -> Unit,
    navigateToInvestmentDetail: (Investment) -> Unit,
    navigateToAddInvestment: () -> Unit,
    onEditInvestment: (Investment) -> Unit,
    onDeleteInvestment: (Investment) -> Unit,
    navigateToNotes: () -> Unit,
    navigateToNoteDetail: (Note) -> Unit,
    navigateToAddNote: () -> Unit,
    onEditNote: (Note) -> Unit,
    onDeleteNote: (Note) -> Unit
) {

    if (state.loading) {
        return
    }

    var expenses: List<ExpenseListItem> by remember { mutableStateOf(emptyList()) }
    var investments: List<InvestmentListItem> by remember { mutableStateOf(emptyList()) }
    var notes: List<NoteListItem> by remember { mutableStateOf(emptyList()) }
    var selectedDateString = ""

    DatesHeader(analyticsHelper) { selectedDate ->
        val newExpenseList = state.expenses
            .filter {
                it.date.toFormattedDateString() == selectedDate.date.toFormattedDateString()
            }
            .sortedByDescending { it.date }
            .map { ExpenseListItem.ExpenseItem(it, EXPENSES_OFFSET) }

        val newInvestmentList = state.investments
            .filter {
                it.date.toFormattedDateString() == selectedDate.date.toFormattedDateString()
            }
            .sortedByDescending { it.date }
            .map { InvestmentListItem.InvestmentItem(it, INVESTMENTS_OFFSET) }

        val newNoteList = state.notes
            .filter {
                it.date.toFormattedDateString() == selectedDate.date.toFormattedDateString()
            }
            .sortedByDescending { it.date }
            .map { NoteListItem.NoteItem(it, NOTES_OFFSET) }

        expenses = newExpenseList
        investments = newInvestmentList
        notes = newNoteList
        selectedDateString = selectedDate.date.toFormattedDateString()
        analyticsHelper.logEvent(AnalyticsEvents.HOME_NEW_DATE_SELECTED)
    }

    LazyColumn(
        modifier = Modifier,
    ) {
        if (expenses.isEmpty() && investments.isEmpty() && notes.isEmpty()) {
            item {
                EmptyCard(analyticsHelper, selectedDateString)
            }
        }

        if (expenses.isNotEmpty()) {
            item {
                CategoryTitleBar(
                    title = stringResource(id = R.string.expenses),
                    onAddClick = {
                        navigateToAddExpense()
                    },
                    onMoreClick = {
                        navigateToExpenses()
                    }
                )
            }

            items(
                items = expenses,
                key = { it.id },
                itemContent = {
                    ExpenseListItem(it, navigateToExpenseDetail, onEditExpense, onDeleteExpense)
                }
            )
        }

        if (investments.isNotEmpty()) {
            item {
                CategoryTitleBar(
                    title = stringResource(id = R.string.investments),
                    onAddClick = {
                        navigateToAddInvestment()
                    },
                    onMoreClick = {
                        navigateToInvestments()
                    }
                )
            }

            items(
                items = investments,
                key = { it.id },
                itemContent = {
                    InvestmentListItem(
                        it,
                        navigateToInvestmentDetail,
                        onEditInvestment,
                        onDeleteInvestment
                    )
                }
            )
        }

        if (notes.isNotEmpty()) {
            item {
                CategoryTitleBar(
                    title = stringResource(id = R.string.notes),
                    onAddClick = {
                        navigateToAddNote()
                    },
                    onMoreClick = {
                        navigateToNotes()
                    }
                )
            }

            items(
                items = notes,
                key = { it.id },
                itemContent = {
                    NoteListItem(it, navigateToNoteDetail, onEditNote, onDeleteNote)
                }
            )
        }
    }
}

@Composable
fun DatesHeader(
    analyticsHelper: AnalyticsHelper,
    onDateSelected: (CalendarModel.DateModel) -> Unit // Callback to pass the selected date
) {
    val dataSource = CalendarDataSource()
    var calendarModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }

    // trigger initial selection callback
    onDateSelected(calendarModel.selectedDate)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        DateHeader(
            data = calendarModel,
            onPrevClickListener = { startDate ->
                // refresh the CalendarModel with new data
                // by get data with new Start Date (which is the startDate-1 from the visibleDates)
                val calendar = Calendar.getInstance()
                calendar.time = startDate

                calendar.add(Calendar.DAY_OF_YEAR, -2) // Subtract one day from startDate
                val finalStartDate = calendar.time

                calendarModel = dataSource.getData(
                    startDate = finalStartDate,
                    lastSelectedDate = calendarModel.selectedDate.date
                )
                analyticsHelper.logEvent(AnalyticsEvents.HOME_CALENDAR_PREVIOUS_WEEK_CLICKED)
            },
            onNextClickListener = { endDate ->
                // refresh the CalendarModel with new data
                // by get data with new Start Date (which is the endDate+2 from the visibleDates)
                val calendar = Calendar.getInstance()
                calendar.time = endDate

                calendar.add(Calendar.DAY_OF_YEAR, 2)
                val finalStartDate = calendar.time

                calendarModel = dataSource.getData(
                    startDate = finalStartDate,
                    lastSelectedDate = calendarModel.selectedDate.date
                )
                analyticsHelper.logEvent(AnalyticsEvents.HOME_CALENDAR_NEXT_WEEK_CLICKED)
            }
        )
        DateList(
            data = calendarModel,
            onDateClickListener = { date ->
                calendarModel = calendarModel.copy(
                    selectedDate = date,
                    visibleDates = calendarModel.visibleDates.map {
                        it.copy(
                            isSelected = it.date.toFormattedDateString() == date.date.toFormattedDateString()
                        )
                    }
                )
                onDateSelected(date)
            }
        )
    }
}

@Composable
fun DateList(
    data: CalendarModel,
    onDateClickListener: (CalendarModel.DateModel) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(items = data.visibleDates) { date ->
            DateItem(date, onDateClickListener)
        }
    }
}

@Composable
fun DateItem(
    date: CalendarModel.DateModel,
    onClickListener: (CalendarModel.DateModel) -> Unit,
) {
    Column {
        Text(
            text = date.day, // day "Mon", "Tue"
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.outline
        )
        Card(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 4.dp)
                .clickable { onClickListener(date) },
            colors = cardColors(
                // background colors of the selected date
                // and the non-selected date are different
                containerColor = if (date.isSelected) {
                    MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.surface
                }
            ),
        ) {
            Column(
                modifier = Modifier
                    .width(42.dp)
                    .height(42.dp)
                    .padding(8.dp)
                    .fillMaxSize(), // Fill the available size in the Column
                verticalArrangement = Arrangement.Center, // Center vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
            ) {
                Text(
                    text = date.date.toFormattedDateShortString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = if (date.isSelected) {
                        FontWeight.Medium
                    } else {
                        FontWeight.Normal
                    }
                )
            }
        }
    }
}

@Composable
fun DateHeader(
    data: CalendarModel,
    onPrevClickListener: (Date) -> Unit,
    onNextClickListener: (Date) -> Unit
) {
    Row(
        modifier = Modifier.padding(vertical = 16.dp),
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = if (data.selectedDate.isToday) {
                "Today"
            } else {
                data.selectedDate.date.toFormattedMonthDateString()
            },
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        )
        IconButton(onClick = {
            onPrevClickListener(data.startDate.date)
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = "Back"
            )
        }
        IconButton(onClick = {
            onNextClickListener(data.endDate.date)
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = "Next"
            )
        }
    }
}

private const val EXPENSES_OFFSET = 10_000_000L
private const val INVESTMENTS_OFFSET = 20_000_000L
private const val NOTES_OFFSET = 30_000_000L
