package com.example.pillnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pillnotes.databinding.FragmentNewContactDoctorBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.domain.viewmodel.ContactViewModel
import javax.inject.Inject

class NewContactDoctorFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var contactViewModel: ContactViewModel

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
    }

    private fun initContactDoctor() {
        if (contactDoctor != null) {
            binding.apply {

            }
        }
    }
}