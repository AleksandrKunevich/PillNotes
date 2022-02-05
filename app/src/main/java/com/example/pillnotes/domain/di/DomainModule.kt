package com.example.pillnotes.domain.di

import com.example.pillnotes.domain.viewmodel.di.ViewModelModule
import dagger.Module


@Module(
    includes = [
        ViewModelModule::class
    ]
)

interface DomainModule