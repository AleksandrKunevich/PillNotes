package com.example.pillnotes.data.room.di

import android.content.Context
import androidx.room.Room
import com.example.pillnotes.data.room.AppDataBaseRoom
import com.example.pillnotes.data.room.ContactDao
import com.example.pillnotes.data.room.NoteTaskDao
import com.example.pillnotes.data.room.interactor.NoteTaskInteractorImpl
import com.example.pillnotes.presentation.interactor.NoteTaskInteractor
import dagger.Module
import dagger.Provides

@Module
object RoomModule {
    @Provides
    fun provideDataBaseRoom(context: Context): AppDataBaseRoom =
        Room.databaseBuilder(
            context,
            AppDataBaseRoom::class.java,
            "room"
        ).build()

    @Provides
    fun provideContactDao(database: AppDataBaseRoom): ContactDao =
        database.getContactDao()

    @Provides
    fun provideNoteTaskDao(database: AppDataBaseRoom): NoteTaskDao =
        database.getNoteTaskDao()

    @Provides
    fun provideNoteTaskInteractorImpl(interactor: NoteTaskInteractorImpl): NoteTaskInteractor =
        interactor
}
