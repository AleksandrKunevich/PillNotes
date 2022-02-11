package com.example.pillnotes.presentation

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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
import com.example.pillnotes.presentation.recycler.RecyclerClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "class HomeFragment"
        private const val GRANTED = "GRANTED"
        private const val DENIED = "DENIED"
    }

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    private val permissions = arrayOf(
        android.Manifest.permission.READ_CALENDAR,
        android.Manifest.permission.WRITE_CALENDAR,
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.VIBRATE,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

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

    private val adapter by lazy { NoteTaskAdapter(requireContext(), onClickNotes) }

    @Inject
    lateinit var noteTaskViewModel: NoteTaskViewModel

    @Inject
    lateinit var contactViewModel: ContactViewModel

    private lateinit var binding: HomeFragmentBinding
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var isFloatingMenuVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerPermissionListener()
        checkPermissionCalendar()
    }

    override fun onStart() {
        super.onStart()

        initRecycler()
        initObserve()
        binding.floatingAddNote.setOnClickListener {
            changeVisibleFloatingMenu()
            findNavController().navigate(R.id.home_to_newNote)
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

    private fun checkPermissionCalendar() {
        permissions.map { permission ->
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) != PackageManager.PERMISSION_GRANTED -> {
                    pLauncher.launch(permissions)
                }
            }
        }
    }

    private fun registerPermissionListener() {
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { mapPermission ->
            permissions.map { permission ->
                if (mapPermission[permission] == true) {
                    Log.e(TAG, "$permission $GRANTED")
                } else {
                    Log.e(TAG, "$permission $DENIED")
                }
            }
        }
    }
}