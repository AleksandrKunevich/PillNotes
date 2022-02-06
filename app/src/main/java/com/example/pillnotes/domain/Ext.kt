package com.example.pillnotes.domain

import java.text.SimpleDateFormat
import java.util.*

internal fun Long.longToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("yyy/MM/dd hh:mm:00")
    return format.format(date)
}