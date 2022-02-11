package com.example.pillnotes.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
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
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.domain.viewmodel.NoteTaskViewModel
import com.example.pillnotes.presentation.spinner.SpinnerCustomAdapter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewNoteFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var noteTaskViewModel: NoteTaskViewModel

    private lateinit var binding: FragmentNoteNewBinding
    private lateinit var spinnerAdapter: SpinnerCustomAdapter
    private lateinit var textQr: String
    private lateinit var typeQr: String
    private lateinit var cal: Calendar
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

        spinnerAdapter = SpinnerCustomAdapter(
            requireContext(),
            R.layout.spinner_row_priority,
            R.id.spinnerPriorityText,
            resources.getStringArray(R.array.spinnerPriorityText)
        )
    }

    override fun onStart() {
        super.onStart()

        arguments?.let { bundle ->
            textQr = bundle.getString(Constants.TEXT_CODE).toString()
            typeQr = bundle.getString(Constants.TYPE_CODE).toString()
            binding.etNoteTitle.setText("$typeQr $textQr")
        }

        binding.apply {
            spinnerPriority.adapter = spinnerAdapter

            spinnerPriority.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View?,
                        pos: Int, id: Long
                    ) {
                        spinnerPriorityPos = pos
                    }

                    override fun onNothingSelected(arg0: AdapterView<*>?) {}
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
            tvCreate.setOnClickListener {
                noteTaskViewModel.addTask(
                    NoteTask(
                        UUID.randomUUID(),
                        "${binding.etNoteTime.text} ${binding.etNoteDate.text}",
                        "${binding.etNoteTitle.text}",
                        "${binding.spinnerTask.selectedItem}",
                        "result here",
                        false,
                        spinnerPriorityPos
                    )
                )
                findNavController().navigate(R.id.newNote_to_home)
            }
            imgQrScan.setOnClickListener {
                findNavController().navigate(R.id.newNote_to_scanner)
            }
            etNoteTime.setOnClickListener {
                val timeCallBack = OnTimeSetListener { timePickerView, hourOfDay, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    cal.set(Calendar.MINUTE, minute)
                    etNoteTime.text = SimpleDateFormat(Constants.TIME_FORMAT).format(cal.time)
                }
                TimePickerDialog(
                    requireContext(),
                    timeCallBack,
                    cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
                ).show()
            }
            etNoteDate.setOnClickListener {
                val dateCallBack =
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, month)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        etNoteDate.text = SimpleDateFormat(Constants.DATE_FORMAT).format(cal.time)
                    }
                DatePickerDialog(
                    requireContext(),
                    dateCallBack,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_YEAR)
                ).show()
            }
        }
    }
}