package com.example.pillnotes.presentation

import android.content.ContentResolver
import android.content.ContentUris
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
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.databinding.FragmentCalendarBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.longToTime
import com.example.pillnotes.domain.viewmodel.NoteTaskViewModel
import java.lang.Long.parseLong
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class CalendarFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

//    @Inject
//    lateinit var cursor: Cursor

    @Inject
    lateinit var noteTaskViewModel: NoteTaskViewModel

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>

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

        binding.btnDeleteAllEvent.setOnClickListener {
            val cursor = requireContext().contentResolver.query(
                CalendarContract.Events.CONTENT_URI,
                null,
                null,
                null,
                null
            )!!
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
                val status = cursor.getColumnIndex(CalendarContract.Events.STATUS)
                if (cursor.getString(status) == "com.example.pillnotes") {
                    deleteEvent(cursor)
                }
            }
        }
    }

    val perm = arrayOf(
        android.Manifest.permission.READ_CALENDAR,
        android.Manifest.permission.WRITE_CALENDAR,
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.VIBRATE,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION

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
        val cursor = requireContext().contentResolver.query(
            CalendarContract.Events.CONTENT_URI,
            null,
            null,
            null,
            null
        )!!
        cursor.moveToFirst()
        while (cursor.moveToNext()) {
            val status = cursor.getColumnIndex(CalendarContract.Events.STATUS)
            if (cursor.getString(status) == "com.example.pillnotes") {
                val uidEvents = cursor.getColumnIndex(CalendarContract.Events.UID_2445)
                val titleEvents = cursor.getColumnIndex(CalendarContract.Events.TITLE)
                val descriptionEvents = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION)
                val startEvents = cursor.getColumnIndex(CalendarContract.Events.DTSTART)
                val endEvents = cursor.getColumnIndex(CalendarContract.Events.DTEND)
                val statusEvents = cursor.getColumnIndex(CalendarContract.Events.STATUS)
                val uid = cursor.getString(uidEvents)
                val title = cursor.getString(titleEvents)
                val description = cursor.getString(descriptionEvents)
                val dayStart = cursor.getLong(startEvents).longToTime()
                val dayEnd = cursor.getLong(endEvents).longToTime()
                val statusEvent = cursor.getString(statusEvents)

                Log.e("!!!!!!!", "$title + $description + $dayStart + $dayEnd +  $statusEvent")
            }
        }
    }

    private fun addEventCalendar() {
        val uid = "${noteTaskViewModel.noteTask.value?.get(0)?.uid}"
        val eventTitle = "${noteTaskViewModel.noteTask.value?.get(0)?.title}"
        val eventDescription = "${noteTaskViewModel.noteTask.value?.get(0)?.task}"
        val eventDay = (noteTaskViewModel.noteTask.value?.get(0)?.time)?.removeRange(10, 19)
        val reminderDayTimeStart: String =
            "$eventDay ${(noteTaskViewModel.noteTask.value?.get(0)?.time?.removeRange(0, 11))}"
        val reminderDayTimeEnd: String = reminderDayTimeStart.replaceRange(17, 19, "59")
        val formatter: SimpleDateFormat = SimpleDateFormat(Constants.DATE_FORMAT_24H)
        val startDate: Date = formatter.parse(reminderDayTimeStart)
        val endDate: Date = formatter.parse(reminderDayTimeEnd)
        val cr: ContentResolver = requireContext().contentResolver
        val values: ContentValues = ContentValues()

        values.put(CalendarContract.Events.CALENDAR_ID, 1)
        values.put(CalendarContract.Events.UID_2445, uid)
        values.put(CalendarContract.Events.DTSTART, startDate.time)
        values.put(CalendarContract.Events.DTEND, endDate.time)
        values.put(CalendarContract.Events.TITLE, eventTitle)
        values.put(CalendarContract.Events.DESCRIPTION, eventDescription)
        values.put(CalendarContract.Events.STATUS, "com.example.pillnotes")

        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        values.put(CalendarContract.Events.HAS_ALARM, 1);

        val eventUri: Uri =
            Uri.parse(CalendarContract.Events.CONTENT_URI.toString())
        val uri: Uri? = cr.insert(eventUri, values)
        addReminders(uri)
    }

    private fun addReminders(uri: Uri?) {
        val id = parseLong(uri?.lastPathSegment)
        val reminders: ContentValues = ContentValues()
        reminders.put(CalendarContract.Reminders.EVENT_ID, id)
        reminders.put(
            CalendarContract.Reminders.METHOD,
            CalendarContract.Reminders.METHOD_ALERT
        )
        reminders.put(CalendarContract.Reminders.MINUTES, 1)
        val reminderUri: Uri =
            Uri.parse(CalendarContract.Reminders.CONTENT_URI.toString())
        val remindersUri: Uri? = requireContext().contentResolver.insert(reminderUri, reminders)
    }

    private fun deleteEvent(cursor: Cursor) {
        val longId = cursor.getColumnIndex(CalendarContract.Events._ID)
        val deleteUri = ContentUris.withAppendedId(
            CalendarContract.Events.CONTENT_URI,
            cursor.getLong(longId)
        )
        val rows: Int = requireContext().contentResolver.delete(deleteUri, null, null)
    }
}