package com.example.pillnotes.domain.model

import android.graphics.Bitmap
import android.location.Location
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ContactDoctor(
    val uid: UUID,
    var bitmap: Bitmap,
    val name: String,
    val profession: String,
    val phone: String,
    var location: Location,
    var isLocation: Boolean
): Parcelable
