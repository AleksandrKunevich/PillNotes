package com.example.pillnotes.data.retrofit

import retrofit2.http.GET

interface CatApi {
    @GET("images/search")
    suspend fun getCatRandom(): CatResponse
}