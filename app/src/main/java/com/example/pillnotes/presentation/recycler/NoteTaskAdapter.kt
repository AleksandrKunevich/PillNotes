package com.example.pillnotes.presentation.recycler

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.domain.model.NoteTaskBase

class NoteTaskAdapter(private val context: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    private var items = listOf<NoteTaskBase>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
//        val binding =
//            PillNotesItemBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
        return when (viewType) {
            NoteTaskHeadViewHolder.VIEW_TYPE_HEAD -> NoteTaskHeadViewHolder.createViewHolder(parent)
            NoteBodyViewHolder.VIEW_TYPE_BODY -> NoteBodyViewHolder.createViewHolder(context, parent)
            else -> throw IllegalStateException("Unknown view type: $viewType")
        }
//        return NoteTaskHolder(context, binding, listener)
    }

    override fun onBindViewHolder(taskHolder: BaseViewHolder, position: Int) {
        taskHolder.bindViewHolder(items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is NoteTaskBase.NoteTime -> NoteTaskHeadViewHolder.VIEW_TYPE_HEAD
            is NoteTaskBase.NoteBody -> NoteBodyViewHolder.VIEW_TYPE_BODY
        }
    }

    fun updateList(data: List<NoteTaskBase>) {
        items = data
    }
}