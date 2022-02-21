package com.example.pillnotes.presentation

import android.content.SharedPreferences
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentNewContactDoctorBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.isSound
import com.example.pillnotes.domain.isVibration
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.domain.util.SoundUtils
import com.example.pillnotes.domain.util.VibrationUtils
import com.example.pillnotes.domain.viewmodel.ContactViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val WAIT_TIME = 200L

class NewContactDoctorFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var contactViewModel: ContactViewModel

    @Inject
    lateinit var preference: SharedPreferences

    @Inject
    lateinit var vibrateUtils: VibrationUtils

    @Inject
    lateinit var soundUtils: SoundUtils

    private lateinit var binding: FragmentNewContactDoctorBinding
    private var contactDoctor: ContactDoctor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            if (bundle.getParcelable<NoteTask>(Constants.CONTACT_CODE) != null) {
                contactDoctor = bundle.getParcelable(Constants.CONTACT_CODE)!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewContactDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initContactDoctor()
        binding.apply {
            btnCreate.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    if (contactDoctor != null) {
                        contactViewModel.deleteContact(contactDoctor!!)
                    }
                    takeIf { preference.isVibration() }?.apply { vibrateUtils.runVibrate() }
                    takeIf { preference.isSound() }?.apply { soundUtils.playBeep() }
                    delay(WAIT_TIME)
                    contactViewModel.addContact(
                        ContactDoctor(
                            uid = UUID.randomUUID(),
                            name = etContactName.text.toString(),
                            profession = etContactProfession.text.toString(),
                            phone = etContactPhone.text.toString(),
                            when (cbIsLocation.isChecked) {
                                true -> {
//                                    contactDoctor!!.location
                                    Location(LocationManager.GPS_PROVIDER)
                                }
                                false -> {
                                    Location(LocationManager.GPS_PROVIDER)
                                }
                            },
                            isLocation = cbIsLocation.isChecked
                        )
                    )
                    findNavController().navigate(R.id.newContact_to_contacts)
                }
            }
            etContactName.setOnClickListener {

            }
        }
    }

    private fun initContactDoctor() {
        if (contactDoctor != null) {
            binding.apply {
                etContactName.setText(contactDoctor!!.name)
                etContactProfession.setText(contactDoctor!!.profession)
                etContactPhone.setText(contactDoctor!!.phone)
                cbIsLocation.isChecked = contactDoctor!!.isLocation
                btnCreate.text = getString(R.string.upgrade)
            }
        }
    }
}