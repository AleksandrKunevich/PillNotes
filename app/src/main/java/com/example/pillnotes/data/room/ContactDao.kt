package com.example.pillnotes.data.room

import androidx.room.*
import com.example.pillnotes.data.room.model.ContactDoctorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getAllContact(): Flow<List<ContactDoctorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllContacts(vararg contactDoctors: ContactDoctorEntity)

    @Delete
    fun deleteContact(contactDoctor: ContactDoctorEntity)
}