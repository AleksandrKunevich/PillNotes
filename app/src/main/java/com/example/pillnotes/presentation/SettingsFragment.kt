package com.example.pillnotes.presentation

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.pillnotes.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences_ui)
    }
}