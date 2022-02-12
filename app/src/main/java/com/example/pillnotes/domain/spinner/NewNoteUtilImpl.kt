package com.example.pillnotes.domain.spinner

import android.content.Context
import com.example.pillnotes.R
import javax.inject.Inject

class NewNoteUtilImpl @Inject constructor(private val context: Context) : NewNoteUtil {

    override fun setSpinnerAdapter(): SpinnerCustomAdapter {
        return SpinnerCustomAdapter(
            context,
            R.layout.spinner_row_priority,
            R.id.spinnerPriorityText,
            context.resources.getStringArray(R.array.spinnerPriorityText)
        )
    }
}