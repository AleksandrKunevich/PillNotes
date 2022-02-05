package com.example.pillnotes.domain.di

import com.example.pillnotes.domain.viewmodel.di.NoteTaskViewModelModule
import dagger.Module


@Module(
    includes = [
        NoteTaskViewModelModule::class
    ]
)

interface DomainModule