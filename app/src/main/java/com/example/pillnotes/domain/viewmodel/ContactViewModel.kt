package com.example.pillnotes.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.presentation.interactor.ContactInteractor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactViewModel @Inject constructor(
    private val repository: ContactInteractor
) : ViewModel() {

    private val _contact = MutableSharedFlow<List<ContactDoctor>>()
    val contact: SharedFlow<List<ContactDoctor>> get() = _contact

    init {
        getAllContact()
    }

    private fun getAllContact() {
        viewModelScope.launch {
            _contact.emitAll(
                repository.getAllContact()
            )
        }
    }

    fun addContact(contact: ContactDoctor) {
        viewModelScope.launch {
            repository.insertContact(contact)
        }
    }

    fun deleteContact(contact: ContactDoctor) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }
}