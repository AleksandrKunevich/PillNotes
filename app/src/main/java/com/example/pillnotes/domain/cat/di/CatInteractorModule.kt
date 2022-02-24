package com.example.pillnotes.domain.cat.di

import android.content.Context
import com.example.pillnotes.data.retrofit.CatApi
import com.example.pillnotes.domain.cat.CatInteractorImpl
import com.example.pillnotes.domain.cat.CatInteractorInterface
import dagger.Module
import dagger.Provides

@Module
object CatInteractorModule {

    @Provides
    fun provideCatInteractorImpl(context: Context, catApi: CatApi): CatInteractorInterface =
        CatInteractorImpl(context, catApi)
}