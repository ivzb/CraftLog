package com.ivzb.craftlog.util

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

fun getDateRange(year: Int, month: Int): Pair<Date, Date> {
    val calendar = getCalendarFor(year, month)

    setTimeToBeginningOfDay(calendar)
    val start = calendar.time

    setTimeToEndOfDay(calendar)
    val end = calendar.time

    return start to end
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

private fun getCalendarFor(year: Int, month: Int): Calendar {
    val calendar = GregorianCalendar.getInstance()
    calendar.set(year, month, 0)
    return calendar
}

private fun setTimeToBeginningOfDay(calendar: Calendar) {
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
}

private fun setTimeToEndOfDay(calendar: Calendar) {
    calendar.add(Calendar.MONTH, 1)
    calendar.add(Calendar.SECOND, -1)
}