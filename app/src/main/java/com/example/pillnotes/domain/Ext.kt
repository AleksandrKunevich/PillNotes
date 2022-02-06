package com.example.pillnotes.domain

import java.text.SimpleDateFormat
import java.util.*

internal fun Long.longToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
    return format.format(date)
}

internal fun String.stringTimeToLong(): Long {
    val df = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return df.parse(this).time
}