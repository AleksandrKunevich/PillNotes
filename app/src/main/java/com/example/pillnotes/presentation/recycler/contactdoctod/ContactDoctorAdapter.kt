package com.example.pillnotes.presentation.recycler.contactdoctod

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.databinding.ContactDoctorItemBinding
import com.example.pillnotes.domain.contactdoctor.ContactDoctorListener
import com.example.pillnotes.domain.model.ContactDoctor

class ContactDoctorAdapter(
    private val context: Context,
    private val listener: ContactDoctorListener
) : RecyclerView.Adapter<ContactDoctorHolder>() {

    private var items = listOf<ContactDoctor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactDoctorHolder {
        val binding =
            ContactDoctorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ContactDoctorHolder(context, binding, listener)
    }

    override fun onBindViewHolder(taskHolder: ContactDoctorHolder, position: Int) {
        taskHolder.bindView(items[position])
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(data: List<ContactDoctor>) {
        val sortedData = data.sortedWith(compareBy { it.name })
        items = sortedData
    }
}