package com.example.pillnotes.data.room.interactor

import com.example.pillnotes.data.room.ContactDao
import com.example.pillnotes.domain.model.Contact
import com.example.pillnotes.domain.util.toContact
import com.example.pillnotes.domain.util.toContactEntity
import com.example.pillnotes.presentation.interactor.ContactInteractor
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactInteractorImpl @Inject() constructor(
    private val contactDao: ContactDao
) : ContactInteractor {
    override fun getAllContact(): Flow<List<Contact>> =
        contactDao.getAllContact().map { entities ->
            entities.map { entity ->
                entity.toContact()
            }
        }

    @OptIn(FlowPreview::class)
    override fun insertContact(contact: Contact) {
        {
            contactDao.insertAllContacts(contact.toContactEntity())
        }.asFlow()
    }

    @OptIn(FlowPreview::class)
    override fun deleteContact(contact: Contact) {
        {
            contactDao.deleteContact(contact.toContactEntity())
        }.asFlow()
    }
}