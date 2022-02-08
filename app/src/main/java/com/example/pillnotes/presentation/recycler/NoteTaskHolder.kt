package com.example.pillnotes.presentation.recycler

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.R
import com.example.pillnotes.databinding.PillNotesItemBinding
import com.example.pillnotes.domain.model.NoteTask

class NoteTaskHolder constructor(
    private val context: Context,
    private val itemBinding: PillNotesItemBinding,
    private val listener: RecyclerClickListener
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bindView(item: NoteTask) {
        itemBinding.apply {
            tvItemTime.text = item.time
            tvPillName.text = item.title
            tvPillTask.text = item.task
            if (item.result == null) {
                tvPillResult.height = 0
            } else {
                tvPillResult.text = item.result
            }
            val checkIdDraw = if (item.check) {
                R.drawable.ic_baseline_check_circle_24_done
            } else {
                R.drawable.add_alert_48
            }
            imgCheckFinish.setImageResource(checkIdDraw)
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