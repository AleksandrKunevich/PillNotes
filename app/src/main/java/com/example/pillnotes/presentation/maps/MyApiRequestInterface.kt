package com.example.pillnotes.presentation.maps

import retrofit.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApiRequestInterface {
    @GET("/maps/api/directions/json")
    fun getJson(
        @Query("origin") origin: String?,
        @Query("destination") destination: String?,
        callback: Callback<DirectionResults>
    )
}
