package com.example.pillnotes.domain.calendar

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.longToTime
import com.example.pillnotes.domain.model.NoteTaskBase
import java.lang.Long
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.Int

class CalendarReminderImpl @Inject constructor(private val context: Context) : CalendarReminder {

    companion object {
        const val TAG = "class CalendarReminderImpl"
    }

    var timeBase = ""
    var uidBase = UUID.randomUUID()
    var nameBase = ""
    var taskBase = ""
    var resultBase = ""
    var checkBase = false
    var priorityBase = 0
    var rruleBase = ""

    override fun addEventCalendar(noteTask: List<NoteTaskBase>) {

        noteTask.forEach {
            when (it) {
                is NoteTaskBase.NoteTime -> {
                    timeBase = it.time
                }
                is NoteTaskBase.NoteBody -> {
                    uidBase = it.uid
                    nameBase = it.title
                    taskBase = it.task
                    resultBase = it.result.toString()
                    checkBase = it.check
                    priorityBase = it.priority
                    rruleBase = it.rrule
                }
            }
        }

        val uid = uidBase.toString()
        val eventTitle = nameBase
        val eventDescription = taskBase
        val eventDay = timeBase.substring(6, 16)
        val eventTime = timeBase.substring(0, 5)
        val reminderDayTimeStart = "$eventTime $eventDay"
//        val reminderDayTimeEnd = "12:12 01/01/2099"
        val reminderDayTimeEnd = reminderDayTimeStart
        Log.e(TAG, "$reminderDayTimeEnd")

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
        values.put(CalendarContract.Events.RRULE, rruleBase);
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
        val remindersUri: Uri? = context.contentResolver.insert(reminderUri, reminders)
    }

    override fun deleteEvent(noteTask: NoteTaskBase) {
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
            if (cursor.getString(uid) == uidBase.toString()) {
                val longId = cursor.getColumnIndex(CalendarContract.Events._ID)
                val deleteUri = ContentUris.withAppendedId(
                    CalendarContract.Events.CONTENT_URI,
                    cursor.getLong(longId)
                )
                val rows: Int = context.contentResolver.delete(deleteUri, null, null)
            }
        }
    }

    override fun showEventCalendar() {
        val cursor = context.contentResolver.query(
            CalendarContract.Events.CONTENT_URI,
            null,
            null,
            null,
            null
        )!!
        cursor.moveToFirst()
        while (cursor.moveToNext()) {
            val status = cursor.getColumnIndex(CalendarContract.Events.STATUS)
            val uidEvents = cursor.getColumnIndex(CalendarContract.Events.UID_2445)
            val titleEvents = cursor.getColumnIndex(CalendarContract.Events.TITLE)
            val rruleEvents = cursor.getColumnIndex(CalendarContract.Events.RRULE)
            val descriptionEvents = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION)
            val startEvents = cursor.getColumnIndex(CalendarContract.Events.DTSTART)
            val endEvents = cursor.getColumnIndex(CalendarContract.Events.DTEND)
            val statusEvents = cursor.getColumnIndex(CalendarContract.Events.STATUS)
            val uid = cursor.getString(uidEvents)
            val title = cursor.getString(titleEvents)
            val rrule = cursor.getString(rruleEvents)
            val description = cursor.getString(descriptionEvents)
            val dayStart = cursor.getLong(startEvents).longToTime()
            val dayEnd = cursor.getLong(endEvents).longToTime()
            val statusEvent = cursor.getString(statusEvents)
            Log.e(
                TAG, """
                       id: $uid
                       title: $title
                       description: $description
                       start: $dayStart 
                       rrule: $rrule 
                       end: $dayEnd
                       status: $statusEvent
                      """.trimIndent()
            )
        }
    }
}