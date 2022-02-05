package com.example.pillnotes.presentation.interactor

import com.example.pillnotes.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactInteractor {

    fun getAllContact(): Flow<List<Contact>>

    fun insertContact(contact: Contact)

    fun deleteContact(contact: Contact)
}