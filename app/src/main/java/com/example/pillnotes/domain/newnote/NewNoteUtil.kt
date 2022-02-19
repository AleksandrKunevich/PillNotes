package com.example.pillnotes.domain.newnote

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter

interface NewNoteUtil {

    fun setSpinnerPriorityAdapter(): SpinnerCustomAdapter

    fun setSpinnerTaskAdapter(): ArrayAdapter<*>

    fun setSpinnerRruleAdapter(): ArrayAdapter<*>

    fun setTime(view: View, contextView: Context)

    fun setDate(view: View, contextView: Context)
}
