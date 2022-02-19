package com.example.pillnotes.domain.calendar

import java.time.LocalDate

interface CalendarWeekOnItemListener {
    fun onItemClick(position: Int, date: LocalDate)
}