package com.example.pillnotes.presentation

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.HomeFragmentBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.calendar.CalendarReminderImpl
import com.example.pillnotes.domain.calendar.CalendarWeekOnItemListener
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.domain.newnote.NoteTaskClickListener
import com.example.pillnotes.domain.util.CalendarUtils
import com.example.pillnotes.domain.viewmodel.NoteTaskViewModel
import com.example.pillnotes.presentation.recycler.calendar.CalendarAdapter
import com.example.pillnotes.presentation.recycler.notetask.NoteTaskAdapter
import java.time.LocalDate
import javax.inject.Inject

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "TAG:com.example.pillnotes.presentation.HomeFragment"
        private const val GRANTED = "GRANTED"
        private const val DENIED = "DENIED"
    }

    private val permissions = arrayOf(
        android.Manifest.permission.READ_CALENDAR,
        android.Manifest.permission.WRITE_CALENDAR,
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.VIBRATE,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var calRem: CalendarReminderImpl

    @Inject
    lateinit var noteTaskViewModel: NoteTaskViewModel

    private lateinit var binding: HomeFragmentBinding
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>
    private val adapterNote by lazy { NoteTaskAdapter(requireContext(), onClickNotes) }
    private val adapterCalendar by lazy { CalendarAdapter(requireContext(), onClickDayWeek) }
    private var monthYear = CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate)

    private var days = arrayListOf<LocalDate>()
    private var listNoteTask = listOf<NoteTask>()
    private var isFloatingMenuVisible = false

    private val onClickNotes: NoteTaskClickListener = object : NoteTaskClickListener {
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
                        0 -> {
                            calRem.deleteEvent(item)
                            noteTaskViewModel.deleteTask(item)
                        }
                    }
                }
                .show()
        }

        override fun onNoteTaskClickListener(item: NoteTask) {
            val bundle = bundleOf(Constants.NOTE_TASK_CODE to item)
            findNavController().navigate(R.id.home_to_newNote, bundle)
        }
    }

    private val onClickDayWeek: CalendarWeekOnItemListener =
        object : CalendarWeekOnItemListener {
            override fun onItemClick(position: Int, date: LocalDate) {
                CalendarUtils.selectedDate = date
                setWeekView()
                setNoteTaskUpdate(listNoteTask)
            }

        }

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
        initRecyclerCalendar()
        setWeekView()
        initRecyclerHome()
        initNoteTask()

        binding.apply {
            btnPreviousWeekAction.setOnClickListener {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1)
                setWeekView()
                setNoteTaskUpdate(listNoteTask)
            }
            btnNextWeekAction.setOnClickListener {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1)
                setWeekView()
                setNoteTaskUpdate(listNoteTask)
            }
            floatingAddNote.setOnClickListener {
                changeVisibleFloatingMenu()
                findNavController().navigate(R.id.home_to_newNote)
            }
            floatingAdd.setOnClickListener {
                changeVisibleFloatingMenu()
                findNavController().navigate(R.id.home_to_contacts)
            }
            floatingMenu.setOnClickListener {
                changeVisibleFloatingMenu()
            }
        }
    }

    private fun changeVisibleFloatingMenu() {
        if (!isFloatingMenuVisible) {
            binding.apply {
                floatingAddNote.show()
                floatingAdd.show()
                tvAddNotes.visibility = View.VISIBLE
                tvAddContact.visibility = View.VISIBLE
            }
            isFloatingMenuVisible = true
        } else {
            binding.apply {
                floatingAddNote.hide()
                floatingAdd.hide()
                tvAddNotes.visibility = View.INVISIBLE
                tvAddContact.visibility = View.INVISIBLE
            }
            isFloatingMenuVisible = false
        }
    }

    private fun initRecyclerHome() {
        binding.apply {
            recyclerHome.adapter = adapterNote
            recyclerHome.layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initRecyclerCalendar() {
        binding.apply {
            recyclerCalendar.adapter = adapterCalendar
            recyclerCalendar.layoutManager = GridLayoutManager(activity, 7)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setWeekView() {
        binding.apply {
            monthYear = CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate)
            monthYearTV.text = monthYear
            days = CalendarUtils.daysInWeekArray(CalendarUtils.selectedDate)
            adapterCalendar.updateList(days)
            recyclerCalendar.post { adapterCalendar.notifyDataSetChanged() }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initNoteTask() {
        noteTaskViewModel.noteTask.observe(viewLifecycleOwner) {
            listNoteTask = it
            setNoteTaskUpdate(listNoteTask)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setNoteTaskUpdate(listNoteTask: List<NoteTask>) {
        adapterNote.updateList(listNoteTask, monthYear, CalendarUtils.selectedDate)
        binding.recyclerHome.post { adapterNote.notifyDataSetChanged() }
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