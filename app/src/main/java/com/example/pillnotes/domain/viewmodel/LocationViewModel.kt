package com.example.pillnotes.domain.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.contactdoctor.location.SharedPreferenceLocationImpl
import javax.inject.Inject

class LocationViewModel @Inject constructor(private val context: Context) : ViewModel() {

    private val _yourLocation = MutableLiveData<Location>()
    val yourLocation: LiveData<Location> get() = _yourLocation

    internal fun setYourLocation(location: Location) {
        _yourLocation.value = location
        val prefs = SharedPreferenceLocationImpl(context)
        prefs.saveLocation(
            Constants.KEY_LOCATION,
            Constants.KEY_LOCATION_2,
            location
        )
    }
}