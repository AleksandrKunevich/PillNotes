package com.example.pillnotes.domain

import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

private const val PREF_VIBRATION = "Vibration"
private const val PREF_SOUND = "Sound"

internal fun Long.longToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat(Constants.DATE_FORMAT_24H)
    return format.format(date)
}

internal fun SharedPreferences.isVibration() = getBoolean(PREF_VIBRATION, true)
internal fun SharedPreferences.isSound() = getBoolean(PREF_SOUND, true)