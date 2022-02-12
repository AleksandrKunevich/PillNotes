package com.example.pillnotes.domain.newnote

import android.content.Context
import android.view.View

interface NewNoteUtil {

    fun setSpinnerAdapter(): SpinnerCustomAdapter

    fun setTime(view: View, contextView: Context)

    fun setDate(view: View, contextView: Context)
}
