package com.example.pillnotes.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pillnotes.data.room.model.ContactEntity
import com.example.pillnotes.data.room.model.NoteTaskBaseEntity
import com.example.pillnotes.data.room.util.UUIDConverter

@Database(
    entities = [ContactEntity::class, NoteTaskBaseEntity::class],
    version = AppDataBaseRoom.VERSION,
    exportSchema = true
)

@TypeConverters
    (
    UUIDConverter::class
)

abstract class AppDataBaseRoom : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }

    abstract fun getContactDao(): ContactDao
    abstract fun getNoteTaskDao(): NoteTaskDao
}