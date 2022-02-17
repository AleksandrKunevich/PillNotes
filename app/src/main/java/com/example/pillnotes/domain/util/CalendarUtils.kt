package com.example.pillnotes.domain.util

import android.os.Build
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CalendarUtils {

    var selectedDate: LocalDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now()
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    fun monthYearFromDate(date: LocalDate): String {
        val formatter: DateTimeFormatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("MMMM yyyy")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return date.format(formatter)
    }

    fun daysInWeekArray(selectedDate: LocalDate): ArrayList<LocalDate> {
        val days: ArrayList<LocalDate> = ArrayList()
        var current: LocalDate? = sundayForDate(selectedDate)
        val endDate: LocalDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            current!!.plusWeeks(1)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        while (current!!.isBefore(endDate)) {
            days.add(current)
            current = current.plusDays(1)
        }
        return days
    }

    private fun sundayForDate(current: LocalDate): LocalDate? {
        var current: LocalDate = current
        val oneWeekAgo: LocalDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            current.minusWeeks(1)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        while (current.isAfter(oneWeekAgo)) {
            if (current.dayOfWeek === DayOfWeek.SUNDAY) return current
            current = current.minusDays(1)
        }
        return null
    }
}