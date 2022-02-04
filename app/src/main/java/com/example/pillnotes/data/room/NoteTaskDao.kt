package com.example.pillnotes.data.room

import androidx.room.*
import com.example.pillnotes.data.room.model.NoteTaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteTaskDao {

    @Query("SELECT * FROM note_task")
    fun getAllNoteTask(): Flow<List<NoteTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg noteTasks: NoteTaskEntity)

    @Delete
    fun delete(noteTask: NoteTaskEntity)

}