package com.example.pillnotes.domain.contactdoctor.location

import android.app.Service
import android.content.Intent
import android.location.LocationListener
import android.location.LocationManager
import android.os.Binder
import android.os.IBinder
import android.provider.Settings
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.domain.viewmodel.LocationViewModel
import javax.inject.Inject

class LocationService : Service() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var locationViewModel: LocationViewModel

    private lateinit var locationManager: LocationManager

    private val locationListener: LocationListener = LocationListener { location ->
        locationViewModel.setYourLocation(location)
    }

    override fun onCreate() {
        super.onCreate()
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
    }

    override fun onBind(intent: Intent?): IBinder {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        return Binder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 1100L, 0f, locationListener
        )

        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER, 1100L, 0f, locationListener
        )
        return START_STICKY
    }
}