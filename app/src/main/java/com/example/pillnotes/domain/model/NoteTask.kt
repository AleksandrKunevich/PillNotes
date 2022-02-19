package com.example.pillnotes.domain.model

import java.util.*

data class NoteTask(
    val uid: UUID,
    val time: String,
    val title: String,
    val task: String,
    val priority: Int,
    val rrule: String
)