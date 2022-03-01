package com.example.pillnotes.presentation

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        private const val EMAIL = "Kunsanba@gmail.com"
        private const val SUBJECT = "Pill Notes"
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
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse(
                "mailto:" + Uri.encode(EMAIL) +
                        "?subject=" + Uri.encode(SUBJECT) +
                        "&body=" + Uri.encode(MESSAGE)
            )
            startActivity(Intent.createChooser(intent, getString(R.string.send_email)))
            true
        }
    }

    override fun onStop() {
        super.onStop()
        onDestroy()
    }
}