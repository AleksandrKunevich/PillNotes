package com.example.pillnotes.presentation.interactor

import com.example.pillnotes.domain.model.ContactDoctor
import kotlinx.coroutines.flow.Flow

interface ContactInteractor {

    fun getAllContact(): Flow<List<ContactDoctor>>

    suspend fun insertContact(contactDoctor: ContactDoctor)

    suspend fun deleteContact(contactDoctor: ContactDoctor)
}