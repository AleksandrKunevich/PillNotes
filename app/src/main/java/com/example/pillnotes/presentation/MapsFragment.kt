package com.example.pillnotes.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pillnotes.R
import com.example.pillnotes.presentation.maps.MyApiRequestInterface
import com.example.pillnotes.presentation.maps2.MainActivity22
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsFragment : Fragment() {

    lateinit var googleMap: GoogleMap

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
////                callBackGoogleMap = OnMapReadyCallback { googleMap ->
////
////                    googleMap.addPolyline(rectLine);
////                    val markerOptions =
////                        MarkerOptions()
////                            .position(LatLng(38.0, 38.0))
////                            .title("Marker in Sydney")
////                    markerOptions.draggable(true);
////                    googleMap.addMarker(markerOptions);
////                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(38.0, 38.0)))
////                }
//            }
//        }
//
//        override fun failure(error: RetrofitError?) {
//
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map22) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
//        GoogleMapsLine().reqinterface
//        val a = getNewsInternet().getJson("-34.0" + "," + "151.0",
//            "-34.5" + "," + "152",
//            callback)
//        a

        startActivity(Intent(requireContext(), MainActivity22::class.java))
    }

    val API_KEY = "AIzaSyAIZRmVsM33sZ3-Xmkn2IpScp5R-LO8y1c"
    val base_url = "http://maps.googleapis.com/"

    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private fun getClient(url: String = base_url) = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getNewsInternet(): MyApiRequestInterface =
        getClient().create(MyApiRequestInterface::class.java)



}

