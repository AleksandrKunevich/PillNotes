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
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentContactsBinding
import com.example.pillnotes.domain.contactdoctor.ContactDoctorListener
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.domain.viewmodel.ContactViewModel
import com.example.pillnotes.presentation.recycler.contactdoctod.ContactDoctorAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

class ContactsFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var contactViewModel: ContactViewModel

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
                .setItems(dialogItem) { dialog, which ->
                    when (which) {
                        0 -> {
                            contactViewModel.deleteContact(item)
                        }
                    }
                }
                .show()
        }

        override fun onContactDoctorClick(item: ContactDoctor) {

        }

        override fun onContactDoctorCallClick(item: ContactDoctor) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${item.phone}")
            startActivity(Intent.createChooser(intent, getString(R.string.choose_call)))
        }

        override fun onContactDoctorMapsClick(item: ContactDoctor) {

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
        initRecyclerContact()
        initObserve()

        binding.apply {
            btnAddContact.setOnClickListener {
                contactViewModel.addContact(
                    ContactDoctor(
                        UUID.randomUUID(),
                        "Doctor name",
                        "superman",
                        "+11111111",
                        Location(LocationManager.GPS_PROVIDER)
                    )
                )
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerContact() {
        binding.apply {
            recyclerContact.adapter = adapterContact
            recyclerContact.layoutManager = LinearLayoutManager(activity)
        }
    }

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
}