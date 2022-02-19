package com.example.pillnotes.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentNoteNewBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.calendar.CalendarReminderImpl
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.domain.newnote.NewNoteUtilImpl
import com.example.pillnotes.domain.viewmodel.NoteTaskViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewNoteFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var noteTaskViewModel: NoteTaskViewModel

    @Inject
    lateinit var calRem: CalendarReminderImpl

    @Inject
    lateinit var newNoteUtil: NewNoteUtilImpl

    private lateinit var binding: FragmentNoteNewBinding
    private lateinit var textQr: String
    private lateinit var typeQr: String
    private lateinit var cal: Calendar
    private var note: NoteTask? = null
    private var randomUUID = UUID.randomUUID()
    private var isRrule = false
    private var rrule = ""
    private var rrulePos = 0
    private var spinnerTaskPos = 0
    private var spinnerPriorityPos = 0

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteNewBinding.inflate(inflater, container, false)
        cal = Calendar.getInstance(TimeZone.getDefault())
        binding.apply {
            etNoteTime.text = SimpleDateFormat(Constants.TIME_FORMAT).format(cal.time)
            etNoteDate.text = SimpleDateFormat(Constants.DATE_FORMAT).format(cal.time)
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            if (bundle.getString(Constants.TEXT_CODE) != null) {
                textQr = bundle.getString(Constants.TEXT_CODE).toString()
                typeQr = bundle.getString(Constants.TYPE_CODE).toString()
                binding.etNoteTitle.setText("$typeQr $textQr")
            }

            if (bundle.getString(Constants.PERIOD_CODE) != null) {
                binding.spinnerRrule.setSelection(4)
                isRrule = true
            }

            if (bundle.getParcelable<NoteTask>(Constants.NOTE_TASK_CODE) != null) {
                note = bundle.getParcelable<NoteTask>(Constants.NOTE_TASK_CODE)!!
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.apply {
            spinnerPriority.adapter = newNoteUtil.setSpinnerPriorityAdapter()
            spinnerPriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?,
                    pos: Int, id: Long
                ) {
                    spinnerPriorityPos = pos
                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {
                }
            }
            spinnerTask.adapter = newNoteUtil.setSpinnerTaskAdapter()
            spinnerTask.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?,
                    pos: Int, id: Long
                ) {
                    spinnerTaskPos = pos
                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {}
            }
            spinnerRrule.adapter = newNoteUtil.setSpinnerRruleAdapter()
            spinnerRrule.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?,
                    pos: Int, id: Long
                ) {
                    when (pos) {
                        0 -> rrule = Constants.RRULE_ONE_TIME
                        1 -> rrule = Constants.RRULE_DAILY
                        2 -> rrule = Constants.RRULE_WEEKLY
                        3 -> rrule = Constants.RRULE_MONTHLY
                        4 -> rrule = Constants.RRULE_YEARLY
                    }
                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {}
            }
            initNote()
            tvCreate.setOnClickListener {
                val newNote = NoteTask(
                    randomUUID,
                    time = "${binding.etNoteTime.text} ${binding.etNoteDate.text}",
                    title = "${binding.etNoteTitle.text}",
                    task = "${binding.spinnerTask.selectedItem}",
                    priority = spinnerPriorityPos,
                    rrule = rrule
                )
                if (note != null) {
                    noteTaskViewModel.deleteTask(note!!)
                }
                noteTaskViewModel.addTask(newNote)
                calRem.addEventCalendar(newNote)
                findNavController().navigate(R.id.newNote_to_home)
            }
            imgQrScan.setOnClickListener { findNavController().navigate(R.id.newNote_to_scanner) }
            newNoteUtil.setTime(etNoteTime, requireContext())
            newNoteUtil.setDate(etNoteDate, requireContext())
        }
    }

    private fun initNote() {
        if (note != null) {
            randomUUID = note!!.uid
            binding.apply {
                etNoteTitle.setText(note!!.title)
                etNoteTime.text =
                    note!!.time.substring(Constants.TIME_START_INDEX, Constants.TIME_END_INDEX)
                etNoteDate.text =
                    note!!.time.substring(Constants.DAY_START_INDEX, Constants.DAY_END_INDEX)
                val arraySpinnerTask =
                    requireContext().resources.getStringArray(R.array.spinnerTaskText)
                spinnerTaskPos = when (note!!.task) {
                    arraySpinnerTask[0] -> 0
                    arraySpinnerTask[1] -> 1
                    arraySpinnerTask[2] -> 2
                    else -> 0
                }
                spinnerTask.setSelection(spinnerTaskPos)
                spinnerPriorityPos = note!!.priority
                spinnerPriority.setSelection(spinnerPriorityPos)
                rrulePos = when (note!!.rrule) {
                    Constants.RRULE_ONE_TIME -> 0
                    Constants.RRULE_DAILY -> 1
                    Constants.RRULE_WEEKLY -> 2
                    Constants.RRULE_MONTHLY -> 3
                    Constants.RRULE_YEARLY -> 4
                    else -> 0
                }
                spinnerRrule.setSelection(rrulePos)
                rrule = note!!.rrule
            }
        }
    }
}