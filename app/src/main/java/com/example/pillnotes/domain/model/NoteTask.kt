package com.example.pillnotes.domain.model

import java.util.*

data class NoteTask(
    val uid: UUID,
    val time: String,
    val name: String,
    val task: String,
    val result: String?,
    val check: Boolean,
    val priority: Int
)