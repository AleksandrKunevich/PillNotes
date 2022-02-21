package com.example.pillnotes.domain.viewmodel.di

import com.example.pillnotes.data.room.interactor.ContactInteractorImpl
import com.example.pillnotes.data.room.interactor.NoteTaskInteractorImpl
import com.example.pillnotes.domain.viewmodel.ContactViewModel
import com.example.pillnotes.domain.viewmodel.LocationViewModel
import com.example.pillnotes.domain.viewmodel.NoteTaskViewModel
import dagger.Module
import dagger.Provides

@Module
object ViewModelModule {

    @Provides
    fun provideNoteTaskViewModel(repository: NoteTaskInteractorImpl): NoteTaskViewModel =
        NoteTaskViewModel(repository)

    @Provides
    fun provideContactViewModel(repository: ContactInteractorImpl): ContactViewModel =
        ContactViewModel(repository)

    @Provides
    fun provideLocationViewModel(): LocationViewModel =
        LocationViewModel()
}