package com.example.pillnotes.presentation.di

import dagger.Module

@Module(
    includes = [
        CalendarModule::class
    ]
)

interface PresentationModule
