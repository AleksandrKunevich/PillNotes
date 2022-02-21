package com.example.pillnotes.domain

import android.content.SharedPreferences

private const val PREF_VIBRATION = "Vibration"
private const val PREF_SOUND = "Sound"

internal fun SharedPreferences.isVibration() = getBoolean(PREF_VIBRATION, true)

internal fun SharedPreferences.isSound() = getBoolean(PREF_SOUND, true)