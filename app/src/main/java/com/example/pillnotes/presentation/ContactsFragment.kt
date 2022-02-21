package com.example.pillnotes.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentContactsBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.contactdoctor.ContactDoctorListener
import com.example.pillnotes.domain.contactdoctor.location.LocationService
import com.example.pillnotes.domain.contactdoctor.location.SharedPreferenceLocationImpl
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.domain.viewmodel.ContactViewModel
import com.example.pillnotes.domain.viewmodel.LocationViewModel
import com.example.pillnotes.presentation.recycler.contactdoctod.ContactDoctorAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ContactsFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var contactViewModel: ContactViewModel

    @Inject
    lateinit var locationViewModel: LocationViewModel

    private lateinit var binding: FragmentContactsBinding
    private var listContacts = listOf<ContactDoctor>()
    private val adapterContact by lazy { ContactDoctorAdapter(requireContext(), onClickContact) }
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val onClickContact: ContactDoctorListener = object : ContactDoctorListener {

        override fun onDeleteClick(item: ContactDoctor) {
            val dialog = AlertDialog.Builder(requireContext())
            val dialogItem = arrayOf(
                getString(R.string.delete),
                getString(R.string.cancel),
            )
            dialog
                .setTitle(getString(R.string.are_you_sure_delete_this))
                .setItems(dialogItem) { _, which ->
                    when (which) {
                        0 -> {
                            contactViewModel.deleteContact(item)
                        }
                    }
                }
                .show()
        }

        override fun onContactDoctorClick(item: ContactDoctor) {
            val bundle = bundleOf(Constants.CONTACT_CODE to item)
            findNavController().navigate(R.id.contacts_to_newContact, bundle)
        }

        override fun onContactDoctorCallClick(item: ContactDoctor) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${item.phone}")
            startActivity(Intent.createChooser(intent, getString(R.string.choose_call)))
        }

        override fun onContactDoctorMapsClick(item: ContactDoctor) {

            if (item.isLocation) {
                if (contactViewModel.isGpsOn()) {
                    val prefs = SharedPreferenceLocationImpl(requireContext())
                    val yourLocation = Location(LocationManager.GPS_PROVIDER)
                    yourLocation.latitude = prefs.getLocation(
                        Constants.KEY_LOCATION, Constants.KEY_LOCATION_2
                    ).first
                    yourLocation.longitude = prefs.getLocation(
                        Constants.KEY_LOCATION, Constants.KEY_LOCATION_2
                    ).second
                    if (yourLocation.latitude != 0.0 && yourLocation.longitude != 0.0) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        val startLatitude = yourLocation.latitude
                        val startLongitude = yourLocation.longitude
                        val endLatitude = item.location.latitude
                        val endLongitude = item.location.longitude
                        intent.data = Uri.parse(
                            "http://maps.google.com/maps?saddr=" +
                                    "$startLatitude," +
                                    "$startLongitude" +
                                    "&daddr=" +
                                    "$endLatitude," +
                                    "$endLongitude"
                        )
                        startActivity(
                            Intent.createChooser(
                                intent,
                                getString(R.string.choose_tracker)
                            )
                        )
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "${item.name} ${getString(R.string.no_location)}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "${getString(R.string.no_location)}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        runService()
        initRecyclerContact()
        initObserve()

        binding.btnAddContact.setOnClickListener {
            findNavController().navigate(R.id.contacts_to_newContact)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerContact() {
        binding.apply {
            recyclerContact.adapter = adapterContact
            recyclerContact.layoutManager = LinearLayoutManager(activity)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setContactUpdate(listContact: List<ContactDoctor>) {
        adapterContact.updateList(listContact)
        binding.recyclerContact.post { adapterContact.notifyDataSetChanged() }
    }

    private fun initObserve() {
        contactViewModel.contact.onEach { listContactDoctor ->
            listContacts = listContactDoctor
            setContactUpdate(listContactDoctor)
        }.launchIn(scope)
    }

    private fun runService() {
        requireContext().startService(
            Intent(
                requireContext(),
                LocationService::class.java
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().stopService(Intent(requireContext(), LocationService::class.java))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }
}