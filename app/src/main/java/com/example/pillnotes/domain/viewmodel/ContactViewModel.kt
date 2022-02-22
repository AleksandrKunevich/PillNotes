package com.example.pillnotes.domain.viewmodel

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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

        if ((ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
                    ) && (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            Toast.makeText(
                context,
                context.getString(R.string.no_location_perm),
                Toast.LENGTH_SHORT
            )
                .show()
            return false
        } else {
            if (!(context.getSystemService(Service.LOCATION_SERVICE) as LocationManager)
                    .isProviderEnabled(LocationManager.GPS_PROVIDER)
            ) {
                Toast.makeText(
                    context,
                    context.getString(R.string.lets_on_gps),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else return true
            return false
        }
    }
}