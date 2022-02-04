package com.example.pillnotes.data.room

import androidx.room.*
import com.example.pillnotes.data.room.model.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getAllContact(): Flow<List<ContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg contacts: ContactEntity)

    @Delete
    fun delete(contact: ContactEntity)
}