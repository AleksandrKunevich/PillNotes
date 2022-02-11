package com.example.pillnotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.databinding.FragmentCalendarBinding
import com.example.pillnotes.domain.calendar.CalendarReminderImpl
import com.example.pillnotes.domain.viewmodel.NoteTaskViewModel
import javax.inject.Inject

class CalendarFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var noteTaskViewModel: NoteTaskViewModel

    @Inject
    lateinit var calRem: CalendarReminderImpl

    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnShowEventCalendar.setOnClickListener {
            calRem.showEventCalendar()
        }

        binding.btnAddEventCalendar.setOnClickListener {
            calRem.addEventCalendar(noteTaskViewModel.noteTask.value?.get(0)!!)
        }

        binding.btnDeleteAllEvent.setOnClickListener {
            calRem.deleteEvent(noteTaskViewModel.noteTask.value?.get(0)!!)
            noteTaskViewModel.deleteTask(noteTaskViewModel.noteTask.value?.get(0)!!)
        }
    }
}