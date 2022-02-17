package com.example.pillnotes.domain.calendar

import com.example.pillnotes.domain.model.NoteTaskBase

interface CalendarReminder {

    fun addEventCalendar(noteTask: List<NoteTaskBase>)

    fun deleteEvent(noteTask: NoteTaskBase)

    fun showEventCalendar()
}
