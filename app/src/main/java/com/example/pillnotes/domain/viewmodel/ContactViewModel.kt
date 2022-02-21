package com.example.pillnotes.domain.viewmodel

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillnotes.R
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.presentation.interactor.ContactInteractor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactViewModel @Inject constructor(
    private val context: Context,
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

    internal fun isGpsOn(): Boolean {
        if (!(context.getSystemService(Service.LOCATION_SERVICE) as LocationManager)
                .isProviderEnabled(LocationManager.GPS_PROVIDER)
        ) {
            val pictureDialog = AlertDialog.Builder(context)
            pictureDialog.setTitle(context.getString(R.string.lets_on_gps))
            val pictureDialogItem = arrayOf(
                context.getString(R.string.ok),
                context.getString(R.string.cancel)
            )
            pictureDialog.setItems(pictureDialogItem) { dialog, which ->
                when (which) {
                    0 -> context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            }
            pictureDialog.show()
        } else return true
        return false
    }
}