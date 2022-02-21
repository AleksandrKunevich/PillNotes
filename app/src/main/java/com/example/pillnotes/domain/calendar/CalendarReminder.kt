package com.example.pillnotes.domain.calendar

import com.example.pillnotes.domain.model.NoteTask

interface CalendarReminder {

    fun addEventCalendar(noteTask: NoteTask)

    fun deleteEvent(noteTask: NoteTask)
}
