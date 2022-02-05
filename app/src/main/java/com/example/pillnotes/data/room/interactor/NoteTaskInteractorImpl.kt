package com.example.pillnotes.data.room.interactor

import com.example.pillnotes.data.room.NoteTaskDao
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.domain.util.toNoteTask
import com.example.pillnotes.domain.util.toNoteTaskEntity
import com.example.pillnotes.presentation.interactor.NoteTaskInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteTaskInteractorImpl @Inject constructor(
    private val noteTaskDao: NoteTaskDao
) : NoteTaskInteractor {

    override fun getAllNoteTask(): Flow<List<NoteTask>> =
        noteTaskDao.getAllNoteTask()
            .map { entities ->
                entities.map { entity ->
                    entity.toNoteTask()
                }
            }.flowOn(Dispatchers.IO)

    override suspend fun insertNoteTask(noteTask: NoteTask) {
        withContext(Dispatchers.IO) {
            noteTaskDao.insertAllNoteTask(noteTask.toNoteTaskEntity())
        }
    }

    override suspend fun deleteNoteTask(noteTask: NoteTask) {
        withContext(Dispatchers.IO) {
            noteTaskDao.deleteNoteTask(noteTask.toNoteTaskEntity())
        }
    }

}