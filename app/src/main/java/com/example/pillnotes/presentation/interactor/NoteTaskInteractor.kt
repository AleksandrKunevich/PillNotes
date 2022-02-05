package com.example.pillnotes.presentation.interactor

import com.example.pillnotes.domain.model.NoteTask
import kotlinx.coroutines.flow.Flow

interface NoteTaskInteractor {

    fun getAllNoteTask(): Flow<List<NoteTask>>

    fun insertNoteTask(noteTask: NoteTask)

    fun deleteNoteTask(noteTask: NoteTask)

}