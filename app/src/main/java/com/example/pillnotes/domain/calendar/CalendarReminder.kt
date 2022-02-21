package com.example.pillnotes.domain.calendar

import com.example.pillnotes.domain.model.NoteTask

interface CalendarReminder {

    fun addEventCalendar(noteTask: NoteTask): Boolean

    fun deleteEvent(noteTask: NoteTask): Boolean
}
