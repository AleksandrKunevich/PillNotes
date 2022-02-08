package com.example.pillnotes.presentation

import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.HomeFragmentBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.domain.viewmodel.ContactViewModel
import com.example.pillnotes.domain.viewmodel.NoteTaskViewModel
import com.example.pillnotes.presentation.recycler.NoteTaskAdapter
import com.example.pillnotes.presentation.recycler.RecyclerClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    private val onClickNotes: RecyclerClickListener = object : RecyclerClickListener {
        override fun onDeleteClickListener(item: NoteTask) {
            val dialog = AlertDialog.Builder(requireContext())
            val dialogItem = arrayOf(
                getString(R.string.delete),
                getString(R.string.cancel),
            )
            dialog
                .setTitle(getString(R.string.are_you_sure_delete_this))
                .setItems(dialogItem) { dialog, which ->
                    when (which) {
                        0 -> noteTaskViewModel.deleteTask(item)
                    }
                }
                .show()
        }
    }

    private val adapter by lazy { NoteTaskAdapter(onClickNotes) }

    @Inject
    lateinit var noteTaskViewModel: NoteTaskViewModel

    @Inject
    lateinit var contactViewModel: ContactViewModel

    private lateinit var binding: HomeFragmentBinding
    private lateinit var textQr: String
    private lateinit var typeQr: String
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var isFloatingMenuVisible = false

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
            textQr = bundle.getString(Constants.TEXT_CODE).toString()
            typeQr = bundle.getString(Constants.TYPE_CODE).toString()
        }

        initRecycler()
        initObserve()
        binding.floatingAddNote.setOnClickListener {
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
            changeVisibleFloatingMenu()
        }
        binding.floatingAddAlarm.setOnClickListener {
            contactViewModel.addContact(
                ContactDoctor(
                    UUID.randomUUID(),
                    "Doctor name",
                    "superman",
                    "+11111111",
                    Location(LocationManager.GPS_PROVIDER)
                )
            )
            changeVisibleFloatingMenu()
        }
        binding.floatingMenu.setOnClickListener {
            changeVisibleFloatingMenu()
        }
    }

    private fun changeVisibleFloatingMenu() {
        if (!isFloatingMenuVisible) {
            binding.apply {
                floatingAddNote.show()
                floatingAddAlarm.show()
                tvAddNotes.visibility = View.VISIBLE
                tvAddAlarm.visibility = View.VISIBLE
            }
            isFloatingMenuVisible = true
        } else {
            binding.apply {
                floatingAddNote.hide()
                floatingAddAlarm.hide()
                tvAddNotes.visibility = View.INVISIBLE
                tvAddAlarm.visibility = View.INVISIBLE
            }
            isFloatingMenuVisible = false
        }
    }

    private fun initObserve() {

        noteTaskViewModel.noteTask.observe(viewLifecycleOwner) { listNoteTask ->
            adapter.updateList(listNoteTask)
        }

        contactViewModel.contact
            .onEach { listContactDoctor ->

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