//package com.example.pillnotes.presentation.maps2
//
//import retrofit.Call;
//import retrofit.http.GET;
//import retrofit.http.Query;
//
//interface RetrofitMaps {
//        @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyAIZRmVsM33sZ3-Xmkn2IpScp5R-LO8y1c")
//        fun getNearbyPlaces(
//            @Query("type") type: String?,
//            @Query("location") location: String?,
//            @Query("radius") radius: Int
//        ): Call<Example>?
//}