package com.example.pillnotes.presentation.recycler

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.R
import com.example.pillnotes.databinding.PillNotesItemBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.model.NoteTask

class NoteTaskHolder constructor(
    private val context: Context,
    private val itemBinding: PillNotesItemBinding,
    private val listener: RecyclerClickListener
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    @SuppressLint("Recycle")
    fun bindView(item: NoteTask) {
        itemBinding.apply {
            tvTime.text = item.time.substring(Constants.TIME_START_INDEX, Constants.TIME_END_INDEX)
            tvTitle.text = item.title
            val arraySpinnerText = context.resources.getStringArray(R.array.spinnerPeriod)
            tvRrule.text = when (item.rrule) {
                Constants.RRULE_ONE_TIME -> arraySpinnerText[0]
                Constants.RRULE_DAILY -> arraySpinnerText[1]
                Constants.RRULE_WEEKLY -> arraySpinnerText[2]
                Constants.RRULE_MONTHLY -> arraySpinnerText[3]
                Constants.RRULE_YEARLY -> arraySpinnerText[4]
                else -> ""
            }
            tvTask.text = item.task
            imgPriority.setImageResource(
                context
                    .resources
                    .obtainTypedArray(R.array.spinnerPriorityDraw)
                    .getResourceId(item.priority, 0)
            )
            imgDelete.setOnClickListener {
                listener.onDeleteClickListener(item)
            }
        }
    }
}