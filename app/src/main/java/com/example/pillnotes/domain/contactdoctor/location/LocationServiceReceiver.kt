package com.example.pillnotes.domain.contactdoctor.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class LocationServiceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_TIME_TICK) {

        }
    }
}