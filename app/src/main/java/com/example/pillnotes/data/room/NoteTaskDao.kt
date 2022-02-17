package com.example.pillnotes.data.room

import androidx.room.*
import com.example.pillnotes.data.room.model.NoteTaskBaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteTaskDao {

    @Query("SELECT * FROM note_task")
    fun getAllNoteTask(): Flow<List<NoteTaskBaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNoteTask(vararg noteTaskBases: NoteTaskBaseEntity)

    @Delete
    fun deleteNoteTask(noteTaskBase: NoteTaskBaseEntity)

}