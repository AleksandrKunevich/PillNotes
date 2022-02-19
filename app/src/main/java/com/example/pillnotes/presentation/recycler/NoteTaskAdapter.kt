package com.example.pillnotes.presentation.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.databinding.PillNotesItemBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.model.NoteTask

class NoteTaskAdapter(
    private val context: Context,
    private val listener: RecyclerClickListener
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

    fun updateList(data: List<NoteTask>, monthYear: String, daySelected: String) {
        val filteredData = mutableListOf<NoteTask>()
        data.forEach { note ->
            val setDay = note.time.substring(
                Constants.DAY_SELECTED_START_INDEX,
                Constants.DAY_SELECTED_END_INDEX
            )
            val setMonthYear = note.time.substring(
                Constants.MONTH_YEAR_SELECTED_START_INDEX,
                Constants.MONTH_YEAR_SELECTED_END_INDEX
            )
            val incomeDay = daySelected.substring(
                Constants.INCOME_DAY_SELECTED_START_INDEX,
                Constants.INCOME_DAY_SELECTED_END_INDEX
            )
            if ((incomeDay == setDay) && (monthYear == setMonthYear)
            ) {
                filteredData.add(note)
            }
        }
        items = filteredData.toList()
    }
}