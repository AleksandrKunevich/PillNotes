package com.example.pillnotes.presentation.di

import android.content.Context
import android.database.Cursor
import android.provider.CalendarContract
import com.example.pillnotes.di.ApplicationComponent
import dagger.Module
import dagger.Provides

@Module
object CalendarModule {

    @Provides
    fun provideCursor(context: Context): Cursor =
        context.contentResolver.query(
            CalendarContract.Events.CONTENT_URI,
            null,
            null,
            null,
            null
        )!!
}