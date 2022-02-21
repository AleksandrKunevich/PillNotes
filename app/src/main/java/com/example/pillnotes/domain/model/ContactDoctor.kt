package com.example.pillnotes.domain.model

import android.location.Location
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ContactDoctor(
    val uid: UUID,
    val name: String,
    val profession: String,
    val phone: String,
    val location: Location,
    val isLocation: Boolean
): Parcelable
