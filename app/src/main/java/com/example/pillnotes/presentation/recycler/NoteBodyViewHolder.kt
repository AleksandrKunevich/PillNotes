package com.example.pillnotes.presentation.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.pillnotes.R
import com.example.pillnotes.domain.model.NoteTaskBase

private var contextBase: Context? = null

class NoteBodyViewHolder(view: View) : BaseViewHolder(view) {

    companion object {
        const val VIEW_TYPE_BODY = 2

        fun createViewHolder(context: Context, viewGroup: ViewGroup): NoteBodyViewHolder {
            contextBase = context
            return NoteBodyViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(
                        R.layout.pill_notes_item,
                        viewGroup,
                        false
                    )
            )
        }
    }

    private val tvPillName by lazy { view.findViewById<AppCompatTextView>(R.id.tvPillName) }
    private val tvPillTask by lazy { view.findViewById<AppCompatTextView>(R.id.tvPillTask) }
    private val tvPillResult by lazy { view.findViewById<AppCompatTextView>(R.id.tvPillResult) }
    private val imgCheckFinish by lazy { view.findViewById<AppCompatImageView>(R.id.imgCheckFinish) }
    private val imgPriority by lazy { view.findViewById<AppCompatImageView>(R.id.imgPriority) }

    override fun bindViewHolder(item: NoteTaskBase) {

        val noteItem = item as NoteTaskBase.NoteBody

        tvPillName.text = noteItem.title
        tvPillTask.text = noteItem.task
        if (noteItem.result == null) {
            tvPillResult.height = 0
        } else {
            tvPillResult.text = noteItem.result
        }
        val checkIdDraw = if (noteItem.check) {
            R.drawable.ic_baseline_check_circle_24_done
        } else {
            R.drawable.add_alert_48
        }
        imgCheckFinish.setImageResource(checkIdDraw)
        imgPriority.setImageResource(
            contextBase
                ?.resources
                ?.obtainTypedArray(R.array.spinnerPriorityDraw)
                ?.getResourceId(noteItem.priority, 0) ?: 0
        )
    }
}