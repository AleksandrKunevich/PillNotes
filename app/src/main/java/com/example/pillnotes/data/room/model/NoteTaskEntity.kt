package com.example.pillnotes.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "note_task")
data class NoteTaskEntity(

    @PrimaryKey
    var uid: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "time")
    val time: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "task")
    val task: String,

    @ColumnInfo(name = "result")
    val result: String?,

    @ColumnInfo(name = "check")
    val check: Boolean,

    @ColumnInfo(name = "priority")
    val priority: Int,

    @ColumnInfo(name = "rrule")
    val rrule: String
)