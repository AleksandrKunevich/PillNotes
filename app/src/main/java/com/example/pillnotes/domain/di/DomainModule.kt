package com.example.pillnotes.domain.di

import com.example.pillnotes.domain.calendar.di.CalendarReminderModule
import com.example.pillnotes.domain.util.di.UtilsModule
import com.example.pillnotes.domain.viewmodel.di.ViewModelModule
import dagger.Module


@Module(
    includes = [
        ViewModelModule::class,
        CalendarReminderModule::class,
        UtilsModule::class
    ]
)

interface DomainModule