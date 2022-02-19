package com.example.pillnotes.domain.newnote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.pillnotes.R

class SpinnerCustomAdapter(
    context: Context,
    resource: Int,
    textViewResourceId: Int,
    objects: Array<String>
) : ArrayAdapter<String>(context, resource, textViewResourceId, objects) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(
        position: Int, convertView: View?,
        parent: ViewGroup?
    ): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val row: View = inflater.inflate(R.layout.spinner_row_priority, parent, false)
        val spIcon = row.findViewById<ImageView>(R.id.spinnerPriorityImage)
        val spText = row.findViewById<TextView>(R.id.spinnerPriorityRow)
        val imageArray = context.resources.obtainTypedArray(R.array.spinnerPriorityDraw)
        spIcon.setImageResource(imageArray.getResourceId(position, 0))
        spText.text = context.resources.getStringArray(R.array.spinnerPriorityText)[position]
        return row
    }
}