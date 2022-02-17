package com.example.pillnotes.domain.model

import java.util.*

sealed class NoteTaskBase {

    data class NoteTime(
        val time: String
    ) : NoteTaskBase()

    data class NoteBody(
        val uid: UUID,
        val title: String,
        val task: String,
        val result: String?,
        val check: Boolean,
        val priority: Int,
        val rrule: String
    ) : NoteTaskBase()
}