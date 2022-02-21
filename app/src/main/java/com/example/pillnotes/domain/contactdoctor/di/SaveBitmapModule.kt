package com.example.pillnotes.domain.contactdoctor.di

import com.example.pillnotes.domain.contactdoctor.SaveBitmapImpl
import com.example.pillnotes.domain.contactdoctor.SaveBitmapInterface
import dagger.Module
import dagger.Provides

@Module
object SaveBitmapModule {

    @Provides
    fun provideSaveBitmapImpl(): SaveBitmapInterface = SaveBitmapImpl()
}