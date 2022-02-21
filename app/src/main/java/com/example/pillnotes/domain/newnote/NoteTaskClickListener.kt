package com.example.pillnotes.domain.newnote

import com.example.pillnotes.domain.model.NoteTask

interface NoteTaskClickListener {

    fun onDeleteClickListener(item: NoteTask)

    fun onNoteTaskClickListener(item: NoteTask)
}