package com.example.pillnotes.presentation.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.databinding.PillNotesItemBinding
import com.example.pillnotes.domain.model.NoteTask
import javax.inject.Inject

class NoteTaskAdapter @Inject constructor() : RecyclerView.Adapter<NoteTaskHolder>() {

    private var items = listOf<NoteTask>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteTaskHolder {
        val binding =
            PillNotesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return NoteTaskHolder(binding)
    }

    override fun onBindViewHolder(taskHolder: NoteTaskHolder, position: Int) {
        taskHolder.bindView(items[position])
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(data: List<NoteTask>) {
        items = data
        notifyDataSetChanged()
    }
}