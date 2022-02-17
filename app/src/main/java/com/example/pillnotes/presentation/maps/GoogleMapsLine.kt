//package com.example.pillnotes.presentation.maps
//
//import android.graphics.Color
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.android.gms.maps.model.PolylineOptions
//import com.google.gson.FieldNamingPolicy
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import retrofit.Callback
//import retrofit.RestAdapter
//import retrofit.RetrofitError
//import retrofit.client.Response
//
//class GoogleMapsLine {
//
//    lateinit var callBackGoogleMap: OnMapReadyCallback
//
//    val base_url = "http://maps.googleapis.com/";
//
//    val gson: Gson = GsonBuilder()
//        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//        .create()
//
//    val restAdapter: RestAdapter = RestAdapter.Builder()
//        .setEndpoint(base_url)
//        .setLogLevel(RestAdapter.LogLevel.FULL)
//        .build();
//
//    private val callback = object : Callback<DirectionResults> {
//        override fun success(directionResults: DirectionResults?, response: Response?) {
//
//            val routelist: ArrayList<LatLng> = arrayListOf()
//            if (directionResults?.routes?.size!! > 0) {
//                var decodelist: ArrayList<LatLng> = arrayListOf()
//                val routeA: Route = directionResults.routes[0]
//                if (routeA.legs?.size!! > 0) {
//                    val steps: List<Steps>? = routeA.legs[0].steps
//                    var step: Steps
//                    var location: Location
//                    var polyline: String
//                    for (i in 0 until steps!!.size) {
//                        step = steps[i]
//                        location = step.start_location!!
//                        routelist.add(LatLng(location.lat, location.lng))
//                        polyline = step.polyline?.points.toString()
//                        decodelist = RouteDecode.decodePoly(polyline)
//                        routelist.addAll(decodelist);
//                        location = step.end_location!!
//                        routelist.add(LatLng(location.lat, location.lng));
//                    }
//                }
//            }
//            if (routelist.size > 0) {
//                val rectLine: PolylineOptions = PolylineOptions().width(10F).color(Color.RED)
//                for (i in 0 until routelist.size) {
//                    rectLine.add(routelist[i]);
//                }
//
//                    callBackGoogleMap = OnMapReadyCallback { googleMap ->
//
//                        googleMap.addPolyline(rectLine);
//                        val markerOptions =
//                            MarkerOptions()
//                                .position(LatLng(38.0, 38.0))
//                                .title("Marker in Sydney")
//                        markerOptions.draggable(true);
//                        googleMap.addMarker(markerOptions);
//                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(38.0, 38.0)))
//                    }
//            }
//        }
//
//        override fun failure(error: RetrofitError?) {
//
//        }
//    }
//
//    val reqinterface = restAdapter
//        .create(MyApiRequestInterface::class.java)
//        .getJson(
//            "-34.0" + "," + "151.0",
//            "-34.5" + "," + "152",
//            callback
//        )
//}