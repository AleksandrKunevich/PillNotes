package com.example.pillnotes.domain

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT_24H = "yyyy/MM/dd HH:mm:ss"

internal fun Long.longToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat(DATE_FORMAT_24H)
    return format.format(date)
}