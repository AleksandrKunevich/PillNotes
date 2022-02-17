package com.example.pillnotes.presentation.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.domain.model.NoteTaskBase

abstract class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bindViewHolder(item: NoteTaskBase)
}