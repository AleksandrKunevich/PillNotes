package com.example.pillnotes.presentation.interactor

import com.example.pillnotes.domain.model.NoteTaskBase
import kotlinx.coroutines.flow.Flow

interface NoteTaskInteractor {

    fun getAllNoteTask(): Flow<List<NoteTaskBase>>

    suspend fun insertNoteTask(noteTask: NoteTaskBase)

    suspend fun deleteNoteTask(noteTask: NoteTaskBase)

}