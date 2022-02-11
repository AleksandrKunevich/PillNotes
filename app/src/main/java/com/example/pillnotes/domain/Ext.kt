package com.example.pillnotes.domain

import java.text.SimpleDateFormat
import java.util.*

internal fun Long.longToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat(Constants.DATE_FORMAT_24H)
    return format.format(date)
}