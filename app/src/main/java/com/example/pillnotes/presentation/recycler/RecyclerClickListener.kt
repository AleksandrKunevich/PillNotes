package com.example.pillnotes.presentation.recycler

import com.example.pillnotes.domain.model.NoteTask

interface RecyclerClickListener {

    fun onDeleteClickListener(item: NoteTask)
}