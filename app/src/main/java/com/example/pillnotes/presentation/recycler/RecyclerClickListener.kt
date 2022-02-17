package com.example.pillnotes.presentation.recycler

import com.example.pillnotes.domain.model.NoteTaskBase

interface RecyclerClickListener {

    fun onDeleteClickListener(item: NoteTaskBase)
}