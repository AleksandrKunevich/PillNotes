package com.example.pillnotes.presentation.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.R
import com.example.pillnotes.databinding.PillNotesItemBinding
import com.example.pillnotes.domain.model.NoteTask

class NoteTaskHolder constructor(private val itemBinding: PillNotesItemBinding) :
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
                R.drawable.ic_baseline_error_outline_24_alert
            }
            imgCheckFinish.setImageResource(checkIdDraw)
            imgPriority.setImageResource(
                when (item.priority) {
                    1 -> R.drawable.ic_baseline_cancel_24_x
                    2 -> R.drawable.ic_baseline_more_vert_24
                    3 -> R.drawable.ic_baseline_home_48
                    else -> R.drawable.ic_launcher_background
                }
            )
        }
    }
}