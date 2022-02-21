package com.example.pillnotes.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentCameraBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.contactdoctor.SaveBitmapImpl
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.domain.model.NoteTask
import javax.inject.Inject

private const val CAMERA_REQUEST_CODE = 10000001

class CameraFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var saveBitmap: SaveBitmapImpl

    private lateinit var binding: FragmentCameraBinding
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    saveBitmap.saveBitmapInStorage(bitmap, requireContext())
                    contactDoctor!!.bitmap = bitmap
                    val bundle = bundleOf(Constants.CONTACT_CODE to contactDoctor)
                    findNavController().navigate(R.id.camera_to_newContact, bundle)
                }
            }
        }
    }
}
