package com.example.pillnotes.presentation

import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.HomeFragmentBinding
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.domain.viewmodel.ContactViewModel
import com.example.pillnotes.domain.viewmodel.NoteTaskViewModel
import com.example.pillnotes.presentation.recycler.NoteTaskAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

private const val TEXT_CODE = "TEXT_CODE"
private const val TYPE_CODE = "TYPE_CODE"

class HomeFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var adapter: NoteTaskAdapter

    @Inject
    lateinit var noteTaskViewModel: NoteTaskViewModel

    @Inject
    lateinit var contactViewModel: ContactViewModel

    private lateinit var binding: HomeFragmentBinding
    private lateinit var textQr: String
    private lateinit var typeQr: String
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("!!!!!!", "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        arguments?.let { bundle ->
            textQr = bundle.getString(TEXT_CODE).toString()
            typeQr = bundle.getString(TYPE_CODE).toString()
            binding.tvResultQrScan.textSize = 32F
            binding.tvResultQrScan.text = "$typeQr = $textQr"
        }

        initRecycler()
        initObserve()
        binding.button1.setOnClickListener {
            findNavController().navigate(R.id.home_to_scanner)
        }
        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.home_to_maps)
        }
        binding.button3.setOnClickListener {
            findNavController().navigate(R.id.home_to_calendar)
        }
        binding.imgBtnNewTask.setOnClickListener {
            noteTaskViewModel.addTask(
                NoteTask(
                    UUID.randomUUID(),
                    "2022/02/06 20:23:00",
                    "Super Health",
                    "every day at 1 table",
                    "Yuuup",
                    false,
                    2
                )
            )
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

    private fun initObserve() {

        noteTaskViewModel.noteTask.observe(viewLifecycleOwner) { listNoteTask ->
            adapter.updateList(listNoteTask)
        }

        contactViewModel.contact
            .onEach { listContactDoctor ->
                Log.d("!!!!!", "initObserve: ${listContactDoctor.size}")
            }
            .launchIn(scope)
    }

    private fun initRecycler() {
        binding.apply {
            recyclerHome.adapter = adapter
            recyclerHome.layoutManager = LinearLayoutManager(activity)
        }
    }
}