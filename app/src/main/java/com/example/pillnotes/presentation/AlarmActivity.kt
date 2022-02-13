package com.example.pillnotes.presentation

import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.pillnotes.R

class AlarmActivity : AppCompatActivity() {
    var ringtone: Ringtone? = null
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        var notificationUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, notificationUri)
        if (ringtone == null) {
            notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            ringtone = RingtoneManager.getRingtone(this, notificationUri)
        }
        if (ringtone != null) {
            ringtone!!.play()
        }
    }

    override fun onDestroy() {
        if (ringtone != null && ringtone!!.isPlaying) {
            ringtone!!.stop()
        }
        super.onDestroy()
    }
}