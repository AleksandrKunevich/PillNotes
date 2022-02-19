package com.example.pillnotes.presentation.recycler.notetask

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.databinding.PillNotesItemBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.domain.newnote.NoteTaskClickListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NoteTaskAdapter(
    private val context: Context,
    private val listener: NoteTaskClickListener
) : RecyclerView.Adapter<NoteTaskHolder>() {

    private var items = listOf<NoteTask>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteTaskHolder {
        val binding =
            PillNotesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return NoteTaskHolder(context, binding, listener)
    }

    override fun onBindViewHolder(taskHolder: NoteTaskHolder, position: Int) {
        taskHolder.bindView(items[position])
    }

    override fun getItemCount() = items.size

    fun updateList(data: List<NoteTask>, monthYear: String, daySelected: LocalDate) {
        val filteredData = mutableListOf<NoteTask>()
        data.forEach { note ->
            if (isRrule(note, monthYear, daySelected)) {
                filteredData.add(note)
            }
        }
        items = filteredData.toList()
    }

    private fun isRrule(note: NoteTask, monthYear: String, daySelected: LocalDate): Boolean {
        val setDay = note.time.substring(
            Constants.DAY_SELECTED_START_INDEX,
            Constants.DAY_SELECTED_END_INDEX
        )
        val setMonth = note.time.substring(
            Constants.MONTH_SELECTED_START_INDEX,
            Constants.MONTH_SELECTED_END_INDEX
        )
        val incomeDay = daySelected.toString().substring(
            Constants.INCOME_DAY_SELECTED_START_INDEX,
            Constants.INCOME_DAY_SELECTED_END_INDEX
        )

        when (note.rrule) {
            Constants.RRULE_DAILY -> {
                return true
            }
            Constants.RRULE_ONE_TIME -> {
                val setMonthYear = note.time.substring(
                    Constants.MONTH_YEAR_SELECTED_START_INDEX,
                    Constants.MONTH_YEAR_SELECTED_END_INDEX
                )
                if ((incomeDay == setDay) && (monthYear == setMonthYear)) {
                    return true
                }
            }
            Constants.RRULE_WEEKLY -> {
                val selectedDayOfWeek = LocalDate.parse(
                    note.time.substring(Constants.DAY_START_INDEX, Constants.DAY_END_INDEX),
                    DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
                ).dayOfWeek
                if (daySelected.dayOfWeek == selectedDayOfWeek) {
                    return true
                }
            }
            Constants.RRULE_MONTHLY -> {
                if (incomeDay == setDay) {
                    return true
                }
            }
            Constants.RRULE_YEARLY -> {
                val incomeMonth = daySelected.toString().substring(
                    Constants.INCOME_MONTH_SELECTED_START_INDEX,
                    Constants.INCOME_MONTH_SELECTED_END_INDEX
                )
                if ((incomeDay == setDay) && (incomeMonth == setMonth)) {
                    return true
                }
            }
        }
        return false
    }
}