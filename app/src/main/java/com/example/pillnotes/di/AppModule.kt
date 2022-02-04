package com.example.pillnotes.di

import com.example.pillnotes.data.di.DataModule
import com.example.pillnotes.domain.di.DomainModule
import dagger.Module

@Module(
    includes = [
        DataModule::class,
        DomainModule::class,
    ]
)
interface AppModule