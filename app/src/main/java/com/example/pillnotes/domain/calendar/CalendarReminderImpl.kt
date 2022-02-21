package com.example.pillnotes.domain.calendar

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import android.widget.Toast
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.model.NoteTask
import java.lang.Long
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.Exception
import kotlin.Int

class CalendarReminderImpl @Inject constructor(private val context: Context) : CalendarReminder {

    override fun addEventCalendar(noteTask: NoteTask) {
        val uid = noteTask.uid.toString()
        val eventTitle = noteTask.title
        val eventDescription = noteTask.task
        val eventDay = noteTask.time.substring(Constants.DAY_START_INDEX, Constants.DAY_END_INDEX)
        val eventTime =
            noteTask.time.substring(Constants.TIME_START_INDEX, Constants.TIME_END_INDEX)
        val reminderDayTimeStart = "$eventTime $eventDay"
        val reminderDayTimeEnd = reminderDayTimeStart
        val formatter = SimpleDateFormat(Constants.DATE_FORMAT_24H)
        val startDate: Date = formatter.parse(reminderDayTimeStart)
        val endDate: Date = formatter.parse(reminderDayTimeEnd)
        val cr: ContentResolver = context.contentResolver
        val values = ContentValues()
        values.put(CalendarContract.Events.CALENDAR_ID, 1)
        values.put(CalendarContract.Events.DTSTART, startDate.time)
        values.put(CalendarContract.Events.DTEND, endDate.time)
        values.put(CalendarContract.Events.TITLE, eventTitle)
        values.put(CalendarContract.Events.DESCRIPTION, eventDescription)
        values.put(CalendarContract.Events.STATUS, uid)
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        values.put(CalendarContract.Events.RRULE, noteTask.rrule);
        val eventUri: Uri =
            Uri.parse(CalendarContract.Events.CONTENT_URI.toString())
        val uri: Uri? = cr.insert(eventUri, values)

        val id = Long.parseLong(uri?.lastPathSegment)
        val reminders = ContentValues()
        reminders.put(CalendarContract.Reminders.EVENT_ID, id)
        reminders.put(
            CalendarContract.Reminders.METHOD,
            CalendarContract.Reminders.METHOD_ALERT
        )
        reminders.put(CalendarContract.Reminders.MINUTES, 1)
        val reminderUri: Uri =
            Uri.parse(CalendarContract.Reminders.CONTENT_URI.toString())
        try {
            val remindersUri: Uri? = context.contentResolver.insert(reminderUri, reminders)
        } catch (e: Exception) {
            Toast.makeText(context, "Sorry! Calendar ERROR.", Toast.LENGTH_SHORT).show()
            e.stackTrace
        }
    }

    @SuppressLint("Recycle")
    override fun deleteEvent(noteTask: NoteTask) {
        val cursor = context.contentResolver.query(
            CalendarContract.Events.CONTENT_URI,
            null,
            null,
            null,
            null
        )!!
        cursor.moveToFirst()
        while (cursor.moveToNext()) {
            val uid = cursor.getColumnIndex(CalendarContract.Events.STATUS)
            if (cursor.getString(uid) == noteTask.uid.toString()) {
                val longId = cursor.getColumnIndex(CalendarContract.Events._ID)
                val deleteUri = ContentUris.withAppendedId(
                    CalendarContract.Events.CONTENT_URI,
                    cursor.getLong(longId)
                )
                val rows: Int = context.contentResolver.delete(deleteUri, null, null)
            }
        }
    }
}