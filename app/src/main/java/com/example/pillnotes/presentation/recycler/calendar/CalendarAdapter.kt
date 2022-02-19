package com.example.pillnotes.presentation.recycler.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.databinding.CalendarCellBinding
import com.example.pillnotes.domain.calendar.CalendarWeekOnItemListener
import java.time.LocalDate

class CalendarAdapter(
    private val context: Context,
    private val onItemListener: CalendarWeekOnItemListener
) : RecyclerView.Adapter<CalendarViewHolder>() {

    private var days = arrayListOf<LocalDate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding =
            CalendarCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CalendarViewHolder(context, binding, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bindView(days[position])
    }

    override fun getItemCount() = days.size

    fun updateList(data: ArrayList<LocalDate>) {
        days = data
    }
}