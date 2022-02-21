package com.example.pillnotes.data.room.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "contact")
data class ContactDoctorEntity(

    @PrimaryKey
    val uid: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "bitmap")
    val bitmap: Bitmap,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "profession")
    val profession: String,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,

    @ColumnInfo(name = "isLocation")
    val isLocation: Boolean
)