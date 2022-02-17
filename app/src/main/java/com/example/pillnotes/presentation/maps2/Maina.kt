//package com.example.pillnotes.presentation.maps2
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.location.Location
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.FragmentActivity
//import com.example.pillnotes.R
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.GoogleApiAvailability
//import com.google.android.gms.common.api.GoogleApiClient
//import com.google.android.gms.location.LocationListener
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.BitmapDescriptorFactory
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.Marker
//import com.google.android.gms.maps.model.MarkerOptions
//import retrofit.*
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//
//class MainActivity22 : FragmentActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
//    GoogleApiClient.OnConnectionFailedListener, LocationListener {
//    private var mMap: GoogleMap? = null
//    var latitude = 0.0
//    var longitude = 0.0
//    private val PROXIMITY_RADIUS = 10000
//    var mGoogleApiClient: GoogleApiClient? = null
//    var mLastLocation: Location? = null
//    var mCurrLocationMarker: Marker? = null
//    var mLocationRequest: LocationRequest? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_maps)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkLocationPermission()
//        }
//
//        //show error dialog if Google Play Services not available
//        if (!isGooglePlayServicesAvailable) {
//            Log.d("onCreate", "Google Play Services not available. Ending Test case.")
//            finish()
//        } else {
//            Log.d("onCreate", "Google Play Services available. Continuing.")
//        }
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map22) as SupportMapFragment?
//        mapFragment!!.getMapAsync(this)
//    }
//
//    private val isGooglePlayServicesAvailable: Boolean
//        private get() {
//            val googleAPI = GoogleApiAvailability.getInstance()
//            val result = googleAPI.isGooglePlayServicesAvailable(this)
//            if (result != ConnectionResult.SUCCESS) {
//                if (googleAPI.isUserResolvableError(result)) {
//                    googleAPI.getErrorDialog(
//                        this, result,
//                        0
//                    )!!.show()
//                }
//                return false
//            }
//            return true
//        }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
//        mMap!!.isMyLocationEnabled = true
//
//        //Initialize Google Play Services
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//                == PackageManager.PERMISSION_GRANTED
//            ) {
//                buildGoogleApiClient()
//                mMap!!.isMyLocationEnabled = true
//            }
//        } else {
//            buildGoogleApiClient()
//            mMap!!.isMyLocationEnabled = true
//        }
//        val btnRestaurant = findViewById<View>(R.id.btnRestaurant) as Button
//        btnRestaurant.setOnClickListener { build_retrofit_and_get_response("restaurant") }
//        val btnHospital = findViewById<View>(R.id.btnHospital) as Button
//        btnHospital.setOnClickListener { build_retrofit_and_get_response("hospital") }
//        val btnSchool = findViewById<View>(R.id.btnSchool) as Button
//        btnSchool.setOnClickListener { build_retrofit_and_get_response("school") }
//        val btnMosque = findViewById<View>(R.id.btnMosque) as Button
//        btnMosque.setOnClickListener { build_retrofit_and_get_response("mosque") }
//    }
//
//    private fun build_retrofit_and_get_response(type: String) {
//        val url = "https://maps.googleapis.com/maps/"
//        val retrofit = Retrofit.Builder()
//            .baseUrl(url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val service = retrofit.create(RetrofitMaps::class.java)
//        val call: Call<Example>? = service.getNearbyPlaces(
//            type,
//            "$latitude,$longitude", PROXIMITY_RADIUS
//        )
//        call?.enqueue(object : Callback<Example?> {
//
//            override fun onResponse(response: Response<Example?>?, retrofit: retrofit.Retrofit?) {
//                try {
//                    mMap!!.clear()
//                    // This loop will go through all the results and add marker on each location.
//                    for (i in 0 until response?.body()?.results!!.size) {
//                        val lat: Double =
//                            response.body()!!.results[i].geometry!!.location!!.lat!!
//                        val lng: Double =
//                            response.body()!!.results[i].geometry!!.location!!.lng!!
//                        val placeName: String = response.body()!!.results[i].name!!
//                        val vicinity: String = response.body()!!.results[i].vicinity!!
//                        val markerOptions = MarkerOptions()
//                        val latLng = LatLng(lat, lng)
//                        // Position of Marker on Map
//                        markerOptions.position(latLng)
//                        // Adding Title to the Marker
//                        markerOptions.title("$placeName : $vicinity")
//                        // Adding Marker to the Camera.
//                        val m = mMap!!.addMarker(markerOptions)
//                        // Adding colour to the marker
//                        markerOptions.icon(
//                            BitmapDescriptorFactory.defaultMarker(
//                                BitmapDescriptorFactory.HUE_RED
//                            )
//                        )
//                        // move map camera
//                        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//                        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(14f))
//                    }
//                } catch (e: Exception) {
//                    Log.d("onResponse", "There is an error")
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onFailure(t: Throwable?) {
//
//            }
//        })
//    }
//
//    @Synchronized
//    protected fun buildGoogleApiClient() {
//        mGoogleApiClient = GoogleApiClient.Builder(this)
//            .addConnectionCallbacks(this)
//            .addOnConnectionFailedListener(this)
//            .addApi(LocationServices.API)
//            .build()
//        mGoogleApiClient!!.connect()
//    }
//
//    override fun onConnected(bundle: Bundle?) {
//        mLocationRequest = LocationRequest()
//        mLocationRequest!!.interval = 1000
//        mLocationRequest!!.fastestInterval = 1000
//        mLocationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(
//                mGoogleApiClient!!,
//                mLocationRequest!!, this
//            )
//        }
//    }
//
//    override fun onConnectionSuspended(i: Int) {}
//    override fun onLocationChanged(location: Location) {
//        Log.d("onLocationChanged", "entered")
//        mLastLocation = location
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker!!.remove()
//        }
//        //Place current location marker
//        latitude = location.latitude
//        longitude = location.longitude
//        val latLng = LatLng(location.latitude, location.longitude)
//        val markerOptions = MarkerOptions()
//        markerOptions.position(latLng)
//        markerOptions.title("Current Position")
//
//        // Adding colour to the marker
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
//
//        // Adding Marker to the Map
//        mCurrLocationMarker = mMap!!.addMarker(markerOptions)
//
//        //move map camera
//        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
//        Log.d(
//            "onLocationChanged",
//            String.format("latitude:%.3f longitude:%.3f", latitude, longitude)
//        )
//        Log.d("onLocationChanged", "Exit")
//    }
//
//    override fun onConnectionFailed(connectionResult: ConnectionResult) {}
//    fun checkLocationPermission(): Boolean {
//        return if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//
//            // Asking user if explanation is needed
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            ) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//                //Prompt the user once explanation has been shown
//                ActivityCompat.requestPermissions(
//                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                    MY_PERMISSIONS_REQUEST_LOCATION
//                )
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(
//                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                    MY_PERMISSIONS_REQUEST_LOCATION
//                )
//            }
//            false
//        } else {
//            true
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>, grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            MY_PERMISSIONS_REQUEST_LOCATION -> {
//
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.size > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                ) {
//
//                    // permission was granted. Do the
//                    // contacts-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(
//                            this,
//                            Manifest.permission.ACCESS_FINE_LOCATION
//                        )
//                        == PackageManager.PERMISSION_GRANTED
//                    ) {
//                        if (mGoogleApiClient == null) {
//                            buildGoogleApiClient()
//                        }
//                        mMap!!.isMyLocationEnabled = true
//                    }
//                } else {
//
//                    // Permission denied, Disable the functionality that depends on this permission.
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
//                }
//                return
//            }
//        }
//    }
//
//    companion object {
//        const val MY_PERMISSIONS_REQUEST_LOCATION = 99
//    }
//}