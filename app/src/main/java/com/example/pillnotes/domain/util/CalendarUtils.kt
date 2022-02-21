package com.example.pillnotes.domain.util

import com.example.pillnotes.domain.Constants
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CalendarUtils {

    var selectedDate: LocalDate = LocalDate.now()

    fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern(Constants.MONTH_YEAR_FORMAT)
        return date.format(formatter)
    }

    fun daysInWeekArray(selectedDate: LocalDate): ArrayList<LocalDate> {
        val days = ArrayList<LocalDate>()
        var current = sundayForDate(selectedDate)
        val endDate = current!!.plusWeeks(1)
        while (current!!.isBefore(endDate)) {
            days.add(current)
            current = current.plusDays(1)
        }
        return days
    }

    private fun sundayForDate(current: LocalDate): LocalDate? {
        var current = current
        val oneWeekAgo = current.minusWeeks(1)
        while (current.isAfter(oneWeekAgo)) {
            if (current.dayOfWeek == DayOfWeek.SUNDAY) return current
            current = current.minusDays(1)
        }
        return null
    }
}