package com.example.pillnotes.domain.spinner.di

import android.content.Context
import com.example.pillnotes.domain.spinner.NewNoteUtil
import com.example.pillnotes.domain.spinner.NewNoteUtilImpl
import dagger.Module
import dagger.Provides

@Module
object NewNoteUtilModule {

    @Provides
    fun provideNewNoteUtil(context: Context): NewNoteUtil =
        NewNoteUtilImpl(context)
}