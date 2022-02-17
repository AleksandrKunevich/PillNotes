package com.example.pillnotes.presentation.recycler.calendar

import android.content.Context
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.R
import com.example.pillnotes.databinding.CalendarCellBinding
import com.example.pillnotes.domain.util.CalendarUtils
import com.example.pillnotes.presentation.recycler.calendar.CalendarAdapter.OnItemListener
import java.time.LocalDate

class CalendarViewHolder(
    private val context: Context,
    private val itemBinding: CalendarCellBinding,
    private val onItemListener: OnItemListener,
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bindView(item: LocalDate) {
        itemBinding.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tvCellDay.text = item.dayOfMonth.toString()
            }
            if (item == CalendarUtils.selectedDate)
                parentView.setBackgroundColor(
                    context.resources.getColor(
                        R.color.light_gray
                    )
                )
        }
        onItemListener.onItemClick(adapterPosition, item)
    }
}