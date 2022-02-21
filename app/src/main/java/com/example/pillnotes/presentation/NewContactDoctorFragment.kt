package com.example.pillnotes.presentation

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentNewContactDoctorBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.contactdoctor.SaveBitmapImpl
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

class NewContactDoctorFragment : Fragment() {

    companion object {
        private const val WAIT_TIME = 200L
    }

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var saveBitmap: SaveBitmapImpl

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
    private val galleryRequestCode = (1..100).random() + 1_000_000
    private val cameraRequestCode = (1..100).random() + 2_000_000

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
                    contactViewModel.addContact(createContact())
                    findNavController().navigate(R.id.newContact_to_contacts)
                }
            }
            imgTakeLocation.setOnClickListener {
                if (contactViewModel.isGpsOn()) {
                    val bundle = bundleOf(Constants.CONTACT_CODE to createContact())
                    findNavController().navigate(R.id.newContact_to_googleMapsCheck, bundle)
                }
            }
            imgTakePhotoContact.setOnClickListener {
                val pictureDialog = AlertDialog.Builder(requireContext())
                pictureDialog.setTitle(
                    getString(R.string.select_action)
                )
                val pictureDialogItem = arrayOf(
                    getString(R.string.select_gallery),
                    getString(R.string.capture_camera)
                )
                pictureDialog.setItems(pictureDialogItem) { dialog, which ->
                    when (which) {
                        0 -> startGallery()
                        1 -> startCamera()
                    }
                }
                pictureDialog.show()
            }
        }
    }

    private fun initContactDoctor() {
        if (contactDoctor != null) {
            binding.apply {
                imgTakePhotoContact.setImageBitmap(contactDoctor!!.bitmap)
                etContactName.setText(contactDoctor!!.name)
                etContactProfession.setText(contactDoctor!!.profession)
                etContactPhone.setText(contactDoctor!!.phone)
                cbIsLocation.isChecked = contactDoctor!!.isLocation
                btnCreate.text = getString(R.string.upgrade)
            }
        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, galleryRequestCode)
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, cameraRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                galleryRequestCode -> {
                    binding.imgTakePhotoContact.setImageURI(data?.data)
                }
                cameraRequestCode -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    saveBitmap.saveBitmapInStorage(bitmap, requireContext())
                    binding.imgTakePhotoContact.setImageBitmap(bitmap)
                }
            }
        }
    }

    private fun createContact(): ContactDoctor {
        binding.apply {
            return ContactDoctor(
                uid = contactDoctor?.uid ?: UUID.randomUUID(),
                bitmap = imgTakePhotoContact.drawable.toBitmap(),
                name = etContactName.text.toString(),
                profession = etContactProfession.text.toString(),
                phone = etContactPhone.text.toString(),
                when (cbIsLocation.isChecked) {
                    true -> {
                        contactDoctor!!.location
                    }
                    false -> {
                        Location(LocationManager.GPS_PROVIDER)
                    }
                },
                isLocation = cbIsLocation.isChecked
            )
        }
    }
}