package com.example.pillnotes.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class NoteTask(
    val uid: UUID,
    val time: String,
    val title: String,
    val task: String,
    val priority: Int,
    val rrule: String
) : Parcelable