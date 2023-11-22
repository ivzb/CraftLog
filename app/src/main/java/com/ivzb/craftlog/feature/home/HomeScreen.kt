package com.ivzb.craftlog.feature.home

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.analytics.AnalyticsHelper
import com.ivzb.craftlog.domain.model.Expense
import com.waseefakhtar.doseapp.feature.home.viewmodel.HomeState
import com.waseefakhtar.doseapp.feature.home.viewmodel.HomeViewModel
import java.util.Calendar
import java.util.Date

@Composable
fun HomeRoute(
    navController: NavController,
    navigateToExpenseDetail: (Expense) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val analyticsHelper = AnalyticsHelper.getInstance(LocalContext.current)
    val state = viewModel.state
    HomeScreen(navController, analyticsHelper, state, viewModel, navigateToExpenseDetail)
}

@Composable
fun HomeScreen(
    navController: NavController,
    analyticsHelper: AnalyticsHelper,
    state: HomeState,
    viewModel: HomeViewModel,
    navigateToExpenseDetail: (Expense) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LastExpenses(
            navController,
            analyticsHelper,
            state,
            viewModel,
            navigateToExpenseDetail
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyOverviewCard(
    navController: NavController,
    analyticsHelper: AnalyticsHelper,
    expensesToday: List<Expense>
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
//            analyticsHelper.logEvent(AnalyticsEvents.ADD_EXPENSE_CLICKED_DAILY_OVERVIEW)
//            navController.navigate(AddExpenseDestination.route)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyCard(navController: NavController, analyticsHelper: AnalyticsHelper) {
    analyticsHelper.logEvent(AnalyticsEvents.EMPTY_CARD_SHOWN)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(156.dp),
        shape = RoundedCornerShape(36.dp),
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.tertiary
        ),
        onClick = {
//            analyticsHelper.logEvent(AnalyticsEvents.ADD_EXPENSE_CLICKED_EMPTY_CARD)
//            navController.navigate(AddExpenseDestination.route)
        }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(24.dp, 24.dp, 0.dp, 16.dp)
                    .fillMaxWidth(.50F)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                Text(
                    text = "hello there",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                )
//
//                Text(
//                    text = stringResource(R.string.home_screen_empty_card_message),
//                    style = MaterialTheme.typography.titleSmall,
//                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.doctor), contentDescription = ""
//                )
            }
        }
    }
}

@Composable
fun LastExpenses(
    navController: NavController,
    analyticsHelper: AnalyticsHelper,
    state: HomeState,
    viewModel: HomeViewModel,
    navigateToExpenseDetail: (Expense) -> Unit
) {

    var filteredExpenses: List<Expense> by remember { mutableStateOf(emptyList()) }

//    DatesHeader(analyticsHelper) { selectedDate ->
//        val newExpenseList = state.expenses
//            .filter { expense ->
//                expense.expenseTime.toFormattedDateString() == selectedDate.date.toFormattedDateString()
//            }
//            .sortedBy { it.expenseTime }
//
//        filteredExpenses = newExpenseList
//        analyticsHelper.logEvent(AnalyticsEvents.HOME_NEW_DATE_SELECTED)
//    }
//
//    if (filteredExpenses.isEmpty()) {
//        EmptyCard(navController, analyticsHelper)
//    } else {
//        LazyColumn(
//            modifier = Modifier,
//        ) {
//            items(
//                items = filteredExpenses,
//                itemContent = {
//                    ExpenseCard(
//                        expense = it,
//                        navigateToExpenseDetail = { expense ->
//                            navigateToExpenseDetail(expense)
//                        }
//                    )
//                }
//            )
//        }
//    }
}

//@Composable
//fun DatesHeader(
//    analyticsHelper: AnalyticsHelper,
//    onDateSelected: (CalendarModel.DateModel) -> Unit // Callback to pass the selected date
//) {
//    val dataSource = CalendarDataSource()
//    var calendarModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    ) {
//        DateHeader(
//            data = calendarModel,
//            onPrevClickListener = { startDate ->
//                // refresh the CalendarModel with new data
//                // by get data with new Start Date (which is the startDate-1 from the visibleDates)
//                val calendar = Calendar.getInstance()
//                calendar.time = startDate
//
//                calendar.add(Calendar.DAY_OF_YEAR, -2) // Subtract one day from startDate
//                val finalStartDate = calendar.time
//
//                calendarModel = dataSource.getData(
//                    startDate = finalStartDate,
//                    lastSelectedDate = calendarModel.selectedDate.date
//                )
//                analyticsHelper.logEvent(AnalyticsEvents.HOME_CALENDAR_PREVIOUS_WEEK_CLICKED)
//            },
//            onNextClickListener = { endDate ->
//                // refresh the CalendarModel with new data
//                // by get data with new Start Date (which is the endDate+2 from the visibleDates)
//                val calendar = Calendar.getInstance()
//                calendar.time = endDate
//
//                calendar.add(Calendar.DAY_OF_YEAR, 2)
//                val finalStartDate = calendar.time
//
//                calendarModel = dataSource.getData(
//                    startDate = finalStartDate,
//                    lastSelectedDate = calendarModel.selectedDate.date
//                )
//                analyticsHelper.logEvent(AnalyticsEvents.HOME_CALENDAR_NEXT_WEEK_CLICKED)
//            }
//        )
//        DateList(
//            data = calendarModel,
//            onDateClickListener = { date ->
//                calendarModel = calendarModel.copy(
//                    selectedDate = date,
//                    visibleDates = calendarModel.visibleDates.map {
//                        it.copy(
//                            isSelected = it.date.toFormattedDateString() == date.date.toFormattedDateString()
//                        )
//                    }
//                )
//                onDateSelected(date)
//            }
//        )
//    }
//}
//
//@Composable
//fun DateList(
//    data: CalendarModel,
//    onDateClickListener: (CalendarModel.DateModel) -> Unit
//) {
//    LazyRow(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        items(items = data.visibleDates) { date ->
//            DateItem(date, onDateClickListener)
//        }
//    }
//}
//
//@Composable
//fun DateItem(
//    date: CalendarModel.DateModel,
//    onClickListener: (CalendarModel.DateModel) -> Unit,
//) {
//    Column {
//        Text(
//            text = date.day, // day "Mon", "Tue"
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            style = MaterialTheme.typography.titleMedium,
//            fontWeight = FontWeight.Normal,
//            color = MaterialTheme.colorScheme.outline
//        )
//        Card(
//            modifier = Modifier
//                .padding(vertical = 4.dp, horizontal = 4.dp)
//                .clickable { onClickListener(date) },
//            colors = cardColors(
//                // background colors of the selected date
//                // and the non-selected date are different
//                containerColor = if (date.isSelected) {
//                    MaterialTheme.colorScheme.tertiary
//                } else {
//                    MaterialTheme.colorScheme.surface
//                }
//            ),
//        ) {
//            Column(
//                modifier = Modifier
//                    .width(42.dp)
//                    .height(42.dp)
//                    .padding(8.dp)
//                    .fillMaxSize(), // Fill the available size in the Column
//                verticalArrangement = Arrangement.Center, // Center vertically
//                horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
//            ) {
//                Text(
//                    text = date.date.toFormattedDateShortString(),
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = if (date.isSelected) {
//                        FontWeight.Medium
//                    } else {
//                        FontWeight.Normal
//                    }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun DateHeader(
//    data: CalendarModel,
//    onPrevClickListener: (Date) -> Unit,
//    onNextClickListener: (Date) -> Unit
//) {
//    Row(
//        modifier = Modifier.padding(vertical = 16.dp),
//    ) {
//        Text(
//            modifier = Modifier
//                .weight(1f)
//                .align(Alignment.CenterVertically),
//            text = if (data.selectedDate.isToday) {
//                "Today"
//            } else {
//                data.selectedDate.date.toFormattedMonthDateString()
//            },
//            style = MaterialTheme.typography.displaySmall,
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colorScheme.tertiary
//        )
//        IconButton(onClick = {
//            onPrevClickListener(data.startDate.date)
//        }) {
//            Icon(
//                imageVector = Icons.Filled.KeyboardArrowLeft,
//                tint = MaterialTheme.colorScheme.tertiary,
//                contentDescription = "Back"
//            )
//        }
//        IconButton(onClick = {
//            onNextClickListener(data.endDate.date)
//        }) {
//            Icon(
//                imageVector = Icons.Filled.KeyboardArrowRight,
//                tint = MaterialTheme.colorScheme.tertiary,
//                contentDescription = "Next"
//            )
//        }
//    }
//}

sealed class ExpenseListItem {

    data class OverviewItem(
        val expensesToday: List<Expense>,
        val isExpenseListEmpty: Boolean
    ) : ExpenseListItem()

    data class ExpenseItem(val expense: Expense) : ExpenseListItem()

    data class HeaderItem(val headerText: String) : ExpenseListItem()
}