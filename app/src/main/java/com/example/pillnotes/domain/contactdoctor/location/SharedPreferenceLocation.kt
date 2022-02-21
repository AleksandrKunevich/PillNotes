package com.example.pillnotes.domain.contactdoctor.location

import android.location.Location

interface SharedPreferenceLocation {
    fun saveLocation(key: String, key2: String, value: Location)

    fun getLocation(key: String, key2: String): Pair<Double, Double>
}