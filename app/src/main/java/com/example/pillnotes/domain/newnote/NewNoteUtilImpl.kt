package com.example.pillnotes.domain.newnote

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.example.pillnotes.R
import com.example.pillnotes.domain.Constants
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewNoteUtilImpl @Inject constructor(private val context: Context) : NewNoteUtil {

    override fun setSpinnerAdapter(): SpinnerCustomAdapter {
        return SpinnerCustomAdapter(
            context,
            R.layout.spinner_row_priority,
            R.id.spinnerPriorityText,
            context.resources.getStringArray(R.array.spinnerPriorityText)
        )
    }

    override fun setTime(view: View, contextView: Context) {
        view as AppCompatTextView
        val cal = Calendar.getInstance(TimeZone.getDefault())
        view.setOnClickListener {
            val timeCallBack =
                TimePickerDialog.OnTimeSetListener { timePickerView, hourOfDay, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    cal.set(Calendar.MINUTE, minute)
                    view.text = SimpleDateFormat(Constants.TIME_FORMAT).format(cal.time)
                }
            TimePickerDialog(
                contextView,
                timeCallBack,
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
            ).show()
        }
    }

    override fun setDate(view: View, contextView: Context) {
        view as AppCompatTextView
        val cal = Calendar.getInstance(TimeZone.getDefault())
        view.setOnClickListener {
            val dateCallBack =
                DatePickerDialog.OnDateSetListener { datePickerView, year, month, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, month)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    view.text = SimpleDateFormat(Constants.DATE_FORMAT).format(cal.time)
                }
            DatePickerDialog(
                contextView,
                dateCallBack,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) - 1,
                cal.get(Calendar.DAY_OF_YEAR)
            ).show()
        }
    }
}