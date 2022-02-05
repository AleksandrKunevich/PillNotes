package com.example.pillnotes.data.room.interactor

import com.example.pillnotes.data.room.ContactDao
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.domain.util.toContact
import com.example.pillnotes.domain.util.toContactEntity
import com.example.pillnotes.presentation.interactor.ContactInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactInteractorImpl @Inject() constructor(
    private val contactDao: ContactDao
) : ContactInteractor {
    override fun getAllContact(): Flow<List<ContactDoctor>> =
        contactDao.getAllContact().map { entities ->
            entities.map { entity ->
                entity.toContact()
            }
        }

    override suspend fun insertContact(contactDoctor: ContactDoctor) {
        withContext(Dispatchers.IO) {
            contactDao.insertAllContacts(contactDoctor.toContactEntity())
        }
    }

    override suspend fun deleteContact(contactDoctor: ContactDoctor) {
        withContext(Dispatchers.IO) {
            contactDao.deleteContact(contactDoctor.toContactEntity())
        }
    }
}