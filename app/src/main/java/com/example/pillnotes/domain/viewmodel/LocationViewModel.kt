package com.example.pillnotes.domain.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {

    private val _yourLocation = MutableLiveData<Location>()
    val yourLocation: LiveData<Location> get() = _yourLocation

    internal fun setYourLocation(location: Location) {
        _yourLocation.value = location
        Log.e("!!!!!!!", """view: $location   
            |view2  ${yourLocation.value}""".trimMargin())
    }
}