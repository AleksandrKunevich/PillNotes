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
import com.example.pillnotes.presentation.recycler.PillNotesAdapter
import java.util.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    private val items = listOf(
        NoteTask(UUID.randomUUID(), "1", "1", "1", "Ok", true, 1),
        NoteTask(UUID.randomUUID(), "12", "1", "1", "Ok", true, 2),
        NoteTask(UUID.randomUUID(), "13", "1", "1", "mnO", false, 3),
        NoteTask(UUID.randomUUID(), "14", "1", "1", null, false, 1),
        NoteTask(UUID.randomUUID(), "1114", "1", "1", null, false, 1)
    )

    @Inject
    lateinit var adapter: PillNotesAdapter

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
        adapter.updateList(items)

        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_calendarFragment_to_mapsFragment)
        }
    }

    private fun initRecycler() {
        binding.apply {
            recyclerHome.adapter = adapter
            recyclerHome.layoutManager = LinearLayoutManager(activity)
        }
    }
}