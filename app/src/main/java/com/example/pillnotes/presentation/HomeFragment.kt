package com.example.pillnotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.HomeFragmentBinding
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.domain.viewmodel.NoteTaskViewModel
import com.example.pillnotes.presentation.recycler.PillNotesAdapter
import java.util.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var adapter: PillNotesAdapter

    @Inject
    lateinit var noteTaskViewModel: NoteTaskViewModel

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecycler()
        initObserve()
        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_calendarFragment_to_mapsFragment)
        }
        binding.imgBtnNewTask.setOnClickListener {
            noteTaskViewModel.addTask(
                NoteTask(
                    UUID.randomUUID(),
                    "12:34",
                    "Super Health",
                    "every day at 1 table",
                    "Yuuup",
                    false,
                    2
                )
            )
            initObserve()
        }
    }

    private fun initObserve() {
        noteTaskViewModel.getAllTask()
        noteTaskViewModel.noteTask.observe(viewLifecycleOwner) { listNoteTask ->
            adapter.updateList(listNoteTask)
        }
    }

    private fun initRecycler() {
        binding.apply {
            recyclerHome.adapter = adapter
            recyclerHome.layoutManager = LinearLayoutManager(activity)
        }
    }
}