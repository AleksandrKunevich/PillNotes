package com.example.pillnotes.domain

import android.annotation.SuppressLint
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

private const val PREF_VIBRATION = "Vibration"
private const val PREF_SOUND = "Sound"

internal fun SharedPreferences.isVibration() = getBoolean(PREF_VIBRATION, true)

internal fun SharedPreferences.isSound() = getBoolean(PREF_SOUND, true)

@SuppressLint("SimpleDateFormat")
internal fun String.toLongTime(): Long {
    val dateFormat = SimpleDateFormat(Constants.TIME_FORMAT)
    if (this.isEmpty()) return 0
    return dateFormat.parse(this).time
}

@SuppressLint("SimpleDateFormat")
internal fun Long.toStringTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat(Constants.TIME_FORMAT)
    return format.format(date)
}