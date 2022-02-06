package com.example.pillnotes.di

import com.example.pillnotes.data.di.DataModule
import com.example.pillnotes.domain.di.DomainModule
import com.example.pillnotes.presentation.di.PresentationModule
import dagger.Module

@Module(
    includes = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class
    ]
)
interface AppModule