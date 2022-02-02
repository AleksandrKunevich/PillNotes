package com.example.pillnotes.di

import com.example.pillnotes.data.DataModule
import com.example.pillnotes.domain.DomainModule
import dagger.Module

@Module(
    includes = [
        DataModule::class,
        DomainModule::class,
    ]
)
interface AppModule