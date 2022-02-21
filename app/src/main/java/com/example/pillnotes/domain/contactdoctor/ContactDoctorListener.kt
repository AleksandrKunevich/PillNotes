package com.example.pillnotes.domain.contactdoctor

import com.example.pillnotes.domain.model.ContactDoctor

interface ContactDoctorListener {

    fun onDeleteClick(item: ContactDoctor)

    fun onContactDoctorClick(item: ContactDoctor)

    fun onContactDoctorCallClick(item: ContactDoctor)

    fun onContactDoctorMapsClick(item: ContactDoctor)
}