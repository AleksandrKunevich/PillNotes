package com.example.pillnotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.databinding.FragmentNewsPaperBinding

class NewsPaperFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    private lateinit var binding: FragmentNewsPaperBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsPaperBinding.inflate(inflater, container, false)
        return binding.root
    }
}