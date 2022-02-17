package com.example.pillnotes.presentation.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.example.pillnotes.R
import com.example.pillnotes.domain.model.NoteTaskBase

class NoteTaskHeadViewHolder(view: View) : BaseViewHolder(view) {

    companion object {
        const val VIEW_TYPE_HEAD = 1

        fun createViewHolder(viewGroup: ViewGroup): NoteTaskHeadViewHolder {
            return NoteTaskHeadViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(
                        R.layout.pill_note_head_item,
                        viewGroup,
                        false
                    )
            )
        }
    }

    private val headTextView by lazy { view.findViewById<AppCompatTextView>(R.id.tvItemTime) }

    override fun bindViewHolder(item: NoteTaskBase) {
        val headItem = item as NoteTaskBase.NoteTime

        headTextView.text = headItem.time
    }
}