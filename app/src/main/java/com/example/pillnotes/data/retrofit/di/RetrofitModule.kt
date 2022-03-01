package com.example.pillnotes.data.retrofit.di

import com.example.pillnotes.data.retrofit.CatApi
import com.example.pillnotes.domain.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object RetrofitModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_CAT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideCatApi(retrofit: Retrofit): CatApi = retrofit.create(CatApi::class.java)
}