package com.example.pillnotes.domain.calendar.di

import android.content.Context
import com.example.pillnotes.domain.calendar.CalendarReminder
import com.example.pillnotes.domain.calendar.CalendarReminderImpl
import dagger.Module
import dagger.Provides

@Module
object CalendarReminderModule {

    @Provides
    fun provideCalendarReminder(context: Context): CalendarReminder =
        CalendarReminderImpl(context)
}