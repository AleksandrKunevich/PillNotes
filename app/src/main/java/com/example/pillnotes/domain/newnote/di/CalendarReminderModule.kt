package com.example.pillnotes.domain.newnote.di

import android.content.Context
import com.example.pillnotes.domain.newnote.NewNoteUtil
import com.example.pillnotes.domain.newnote.NewNoteUtilImpl
import dagger.Module
import dagger.Provides

@Module
object NewNoteUtilModule {

    @Provides
    fun provideNewNoteUtil(context: Context): NewNoteUtil =
        NewNoteUtilImpl(context)
}