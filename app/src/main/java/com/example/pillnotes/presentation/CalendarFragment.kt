package com.example.pillnotes.presentation

import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pillnotes.databinding.FragmentCalendarBinding
import java.lang.Long.parseLong
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>
    private var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerPermissionListener()
        checkPermissionCalendar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnShowEventCalendar.setOnClickListener {
            showEventCalendar()
        }

        binding.btnAddEventCalendar.setOnClickListener {
            addEventCalendar()
        }
    }

    val perm = arrayOf(
        android.Manifest.permission.READ_CALENDAR,
        android.Manifest.permission.WRITE_CALENDAR
    )

    private fun checkPermissionCalendar() {
        perm.map { permission ->
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) != PackageManager.PERMISSION_GRANTED -> {
                    pLauncher.launch(perm)
                }
            }
        }
    }

    private fun registerPermissionListener() {
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { mapPermission ->
            perm.map { permission ->
                if (mapPermission[permission] == true) {
                    Toast.makeText(requireContext(), "$permission GRANTED", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "$permission DENIED", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showEventCalendar() {
        cursor = requireContext().contentResolver.query(
            CalendarContract.Events.CONTENT_URI,
            null,
            null,
            null,
            null
        ).let {
            requireContext().contentResolver.query(
                CalendarContract.Events.CONTENT_URI,
                null,
                null,
                null,
                null
            )!!
        }
        while (cursor!!.moveToNext()) {
            if (cursor != null) {
                val id_1 = cursor!!.getColumnIndex(CalendarContract.Events._ID)
                val id_2 = cursor!!.getColumnIndex(CalendarContract.Events.TITLE)
                val id_3 = cursor!!.getColumnIndex(CalendarContract.Events.DESCRIPTION)
                val id_4 = cursor!!.getColumnIndex(CalendarContract.Events.EVENT_LOCATION)
                val id_5 = cursor!!.getColumnIndex(CalendarContract.Events.DTSTART)
                val id_6 = cursor!!.getColumnIndex(CalendarContract.Events.DTEND)

                val id = cursor!!.getString(id_1)
                val title = cursor!!.getString(id_2)
                val description = cursor!!.getString(id_3)
                val eventLocation = cursor!!.getString(id_4)
                var dayStart = "-1"
                if (cursor!!.getString(id_5) != null) {
                    dayStart = cursor!!.getString(id_5)
                }
                var dayEnd = "-1"
                if (cursor!!.getString(id_6) != null) {
                    dayStart = cursor!!.getString(id_6)
                }

                Log.e(
                    "!!!!!!!",
                    "$id + $title + $description + $eventLocation + ${convertLongToTime(dayStart.toLong())} + ${
                        convertLongToTime(dayEnd.toLong())
                    }"
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Event empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun addEventCalendar() {
        val eventTitle = "Ready Android X" //This is event title
        val eventDescription = "Always happy" //This is event description
        val eventDate = "02/06/2022" //This is the event date
        val eventLocation = "Taj Mahal"  //This is the address for your event location

        val cal: Calendar = Calendar.getInstance()
        val dateFormat: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
        val dEventDate: Date = dateFormat.parse(eventDate)
        cal.time = dEventDate

        val reminderDate: String = dateFormat.format(cal.time)
        Log.e("!!!!!! Day befe evt sta", reminderDate)
        val reminderDayStart: String = "$reminderDate 11:20:00"
        val reminderDayEnd: String = "$reminderDate 23:59:59"
        var startTimeInMilliseconds: Long = 0L
        var endTimeInMilliseconds: Long = 0L

        val formatter: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
        val startDate: Date = formatter.parse(reminderDayStart)
        val endDate: Date = formatter.parse(reminderDayEnd)
        startTimeInMilliseconds = startDate.time;
        endTimeInMilliseconds = endDate.time;
        Log.e("!!!!!!! StartDate ", "$startTimeInMilliseconds + $reminderDayStart")
        Log.e("!!!!!! EndDate ", "$endTimeInMilliseconds + $reminderDayEnd")

        val cr: ContentResolver = requireContext().contentResolver
        val values: ContentValues = ContentValues()
        values.put(CalendarContract.Events.CALENDAR_ID, 1)
        values.put(CalendarContract.Events.DTSTART, startTimeInMilliseconds)
        values.put(CalendarContract.Events.DTEND, endTimeInMilliseconds)
        values.put(CalendarContract.Events.TITLE, eventTitle)
        values.put(CalendarContract.Events.DESCRIPTION, eventDescription)
        values.put(CalendarContract.Events.EVENT_LOCATION, eventLocation)

        val timeZone: TimeZone = TimeZone.getDefault()
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.id)
        values.put(CalendarContract.Events.RRULE, "FREQ=HOURLY;COUNT=1")
        values.put(CalendarContract.Events.HAS_ALARM, 1);

        val eventUri: Uri =
            Uri.parse("content://com.android.calendar/events")

        // insert event to calendar
        val uri: Uri? = cr.insert(eventUri, values)
        Log.e("!!!!! EventTest", uri.toString())

//add reminder for event
        var id: Long = -1;
        val idd = uri?.lastPathSegment
        id = parseLong(idd)
        val reminders: ContentValues = ContentValues()
        reminders.put(CalendarContract.Reminders.EVENT_ID, id)
        reminders.put(
            CalendarContract.Reminders.METHOD,
            CalendarContract.Reminders.METHOD_ALERT
        )
        reminders.put(CalendarContract.Reminders.MINUTES, 1);
        val reminderUri: Uri =
            Uri.parse("content://com.android.calendar/reminders")
        val remindersUri: Uri? = requireContext().contentResolver.insert(reminderUri, reminders)
        Log.e("!!!!! RemindersTest ", remindersUri.toString())
    }
}

private fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
    return format.format(date)
}
