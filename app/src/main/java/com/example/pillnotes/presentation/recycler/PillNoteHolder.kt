package com.example.pillnotes.presentation.recycler

import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.databinding.PillNotesItemBinding
import com.example.pillnotes.domain.model.NoteTask

class PillNoteHolder private constructor(
    itemBinding: PillNotesItemBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

//    companion object {
//        fun create(parent: ViewGroup) = PillNoteHolder(
//            LayoutInflater
//                .from(parent.context)
//                .inflate(
//                    R.layout.pill_notes_item,
//                    parent,
//                    false
//                )
//        )
//    }

    private val tvPillName: AppCompatTextView by lazy { itemBinding.tvPillName }

    fun bindView(item: NoteTask) {
        tvPillName.text = item.name
    }

}