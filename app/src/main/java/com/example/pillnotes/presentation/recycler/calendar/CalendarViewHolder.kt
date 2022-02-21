package com.example.pillnotes.presentation.recycler.calendar

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.R
import com.example.pillnotes.databinding.CalendarCellBinding
import com.example.pillnotes.domain.calendar.CalendarWeekOnItemListener
import com.example.pillnotes.domain.util.CalendarUtils
import java.time.LocalDate

class CalendarViewHolder(
    private val context: Context,
    private val itemBinding: CalendarCellBinding,
    private var onItemListener: CalendarWeekOnItemListener,
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bindView(item: LocalDate) {
        itemBinding.apply {
            cellDayText.text = item.dayOfMonth.toString()
            if (item == CalendarUtils.selectedDate) {
                cellDayText.setBackgroundColor(context.resources.getColor(R.color.gray, null))
            } else {
                cellDayText.setBackgroundColor(
                    context.resources.getColor(
                        R.color.background_home_fragment,
                        null
                    )
                )
            }
        }
        itemBinding.cellDayText.setOnClickListener {
            onItemListener.onItemClick(adapterPosition, item)
        }
    }
}