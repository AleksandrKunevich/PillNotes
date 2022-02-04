package com.example.pillnotes.domain.model

import android.location.Location
import java.util.*

data class Contact(
    val uid: UUID,
    val name: String,
    val profession: String,
    val phone: String,
    val location: Location
)
