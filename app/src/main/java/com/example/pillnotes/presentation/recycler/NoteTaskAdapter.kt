package com.example.pillnotes.presentation.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.databinding.PillNotesItemBinding
import com.example.pillnotes.domain.model.NoteTask

class NoteTaskAdapter(
    private val context: Context,
    private val listener: RecyclerClickListener
) : RecyclerView.Adapter<NoteTaskHolder>() {

    private var items = listOf<NoteTask>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteTaskHolder {
        val binding =
            PillNotesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return NoteTaskHolder(context, binding, listener)
    }

    override fun onBindViewHolder(taskHolder: NoteTaskHolder, position: Int) {
        taskHolder.bindView(items[position])
    }

    override fun getItemCount() = items.size

    fun updateList(data: List<NoteTask>) {
        items = data
    }
}