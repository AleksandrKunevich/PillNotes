package com.example.pillnotes.presentation

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentSettingsBinding
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        private const val EMAIL = "Kunsanba@gmail.com"
        private const val SUBJECT = "KunsanbaSubject@gmail.com"
        private const val MESSAGE = "Hello dear friend, Aliaksandr"
    }

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var preference: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences_ui)
    }

    override fun onStart() {
        super.onStart()

        val email =
            findPreference<Preference>(requireContext().resources.getString(R.string.key_email_me))
        email?.setOnPreferenceClickListener {
            val intent: Intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, EMAIL)
            intent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT)
            intent.putExtra(Intent.EXTRA_TEXT, MESSAGE)
            try {
                startActivity(Intent.createChooser(intent, "Choose Email Client..."))
            } catch (e: Exception) {
                e.stackTrace
            }
            true
        }
    }
}