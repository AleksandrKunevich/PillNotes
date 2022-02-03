package com.example.pillnotes.domain.model

data class NoteTask(
    val time: String,
    val name: String,
    val task: String,
    val result: String?,
    val check: Boolean,
    val priority: Int
)