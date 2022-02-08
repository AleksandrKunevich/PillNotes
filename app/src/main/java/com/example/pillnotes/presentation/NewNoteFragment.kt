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
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.domain.viewmodel.NoteTaskViewModel
import com.example.pillnotes.presentation.spinner.SpinnerCustomAdapter
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
    private var spinnerTaskPos = 0
    private var spinnerPriorityPos = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteNewBinding.inflate(inflater, container, false)
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

        binding.spinnerPriority.apply {
            adapter = spinnerAdapter
        }
        binding.spinnerPriority.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?,
                    pos: Int, id: Long
                ) {
                    spinnerPriorityPos = pos
                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {}
            }

        binding.spinnerTask.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?,
                    pos: Int, id: Long
                ) {
                    spinnerTaskPos = pos
                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {}
            }
        binding.tvCreate.setOnClickListener {
            noteTaskViewModel.addTask(
                NoteTask(
                    UUID.randomUUID(),
                    "${binding.etNoteDate.text} ${binding.etNoteTime.text}",
                    "${binding.etNoteTitle.text}",
                    "${binding.spinnerTask.selectedItem}",
                    "result here",
                    false,
                    spinnerPriorityPos
                )
            )
            findNavController().navigate(R.id.newNote_to_home)
        }
    }
}