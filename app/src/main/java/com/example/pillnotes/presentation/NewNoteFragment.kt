package com.example.pillnotes.presentation

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
    private var isRrule = false
    private var rrule = ""
    private var spinnerTaskPos = 0
    private var spinnerPriorityPos = 0

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            if (bundle.getString(Constants.TEXT_CODE) != null) {
                textQr = bundle.getString(Constants.TEXT_CODE).toString()
                typeQr = bundle.getString(Constants.TYPE_CODE).toString()
                binding.etNoteTitle.setText("$typeQr $textQr")
            }

            if (bundle.getString(Constants.PERIOD_CODE) != null) {
                binding.spinnerPeriod.setSelection(4)
                isRrule = true
            }
        }
    }

    override fun onStart() {
        super.onStart()

        binding.spinnerPriority.adapter = newNoteUtil.setSpinnerAdapter()
        binding.apply {
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

            spinnerTask.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View?,
                        pos: Int, id: Long
                    ) {
                        spinnerTaskPos = pos
                    }

                    override fun onNothingSelected(arg0: AdapterView<*>?) {}
                }

            spinnerPeriod.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View?,
                        pos: Int, id: Long
                    ) {
                        when (pos) {
                            0 -> rrule = "FREQ=DAILY"
                            1 -> rrule = "FREQ=WEEKLY;INTERVAL=1"
                            2 -> rrule = "FREQ=MONTHLY;INTERVAL=1"
                            3 -> rrule = "FREQ=YEARLY;INTERVAL=1"
                        }
                    }

                    override fun onNothingSelected(arg0: AdapterView<*>?) {}
                }

            tvCreate.setOnClickListener {
                val newNote = NoteTask(
                    UUID.randomUUID(),
                    "${binding.etNoteTime.text} ${binding.etNoteDate.text}",
                    "${binding.etNoteTitle.text}",
                    "${binding.spinnerTask.selectedItem}",
                    "result here",
                    false,
                    spinnerPriorityPos,
                    rrule
                )
                noteTaskViewModel.addTask(newNote)
                calRem.addEventCalendar(newNote)
                findNavController().navigate(R.id.newNote_to_home)
            }
            imgQrScan.setOnClickListener { findNavController().navigate(R.id.newNote_to_scanner) }
            newNoteUtil.setTime(etNoteTime, requireContext())
            newNoteUtil.setDate(etNoteDate, requireContext())
        }
    }
}