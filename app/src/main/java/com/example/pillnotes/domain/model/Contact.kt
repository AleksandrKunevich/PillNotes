package com.example.pillnotes.domain.model

import android.location.Location

data class Contact(
    val name: String,
    val profession: String,
    val phone: String,
    val location: Location
)
