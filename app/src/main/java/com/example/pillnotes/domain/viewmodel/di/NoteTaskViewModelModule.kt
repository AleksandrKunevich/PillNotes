package com.example.pillnotes.domain.viewmodel.di

import com.example.pillnotes.data.room.interactor.NoteTaskInteractorImpl
import com.example.pillnotes.domain.viewmodel.NoteTaskViewModel
import dagger.Module
import dagger.Provides

@Module
object NoteTaskViewModelModule {

    @Provides
    fun provideNoteTaskViewModel(repository: NoteTaskInteractorImpl): NoteTaskViewModel =
        NoteTaskViewModel(repository)

}