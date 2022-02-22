package com.example.pillnotes.domain.contactdoctor.location

import android.content.Context
import android.location.Location
import android.util.Log

class SharedPreferenceLocationImpl(private val context: Context) : SharedPreferenceLocation {

    companion object {
        private const val SHARED_PREFS_NAME = "SHARED_PREFS_LOCATION"
        private const val DEFAULT_LATITUDE = 0.0f
        private const val DEFAULT_LONGITUDE = 0.0f
    }

    private val prefs by lazy {
        context.getSharedPreferences(
            SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }

    override fun saveLocation(key: String, key2: String, value: Location) {
        Log.e("!!!!!!!!!", "saveLocation: $value")
        prefs.edit()
            .putFloat(key, value.latitude.toFloat())
            .putFloat(key2, value.longitude.toFloat())
            .apply()
    }

    override fun getLocation(key: String, key2: String): Pair<Double, Double> {
        return Pair(
            prefs.getFloat(key, DEFAULT_LATITUDE).toDouble(),
            prefs.getFloat(key2, DEFAULT_LONGITUDE).toDouble()
        )
    }
}