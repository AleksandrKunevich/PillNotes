package com.example.pillnotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.databinding.FragmentCatBinding
import com.example.pillnotes.domain.viewmodel.CatViewModel
import javax.inject.Inject

class CatFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var catViewModel: CatViewModel

    private lateinit var binding: FragmentCatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCat()
    }

    override fun onStart() {
        super.onStart()
        binding.imgCat.setOnClickListener {
            loadCat()
        }

        catViewModel.draw?.observe(this) { bitmap ->
            binding.imgCat.setImageBitmap(bitmap)
        }
    }

    private fun loadCat() {
        catViewModel.getCatRandomBitmap()
    }
}