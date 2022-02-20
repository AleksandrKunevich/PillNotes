package com.example.pillnotes.domain.util

import android.content.Context
import android.media.MediaPlayer
import com.example.pillnotes.R

class SoundUtils(private val context: Context) {
    fun playBeep() {
        try {
            val mMediaPlayer = MediaPlayer.create(context, R.raw.beep)
            mMediaPlayer.setVolume(1f, 1f)
            mMediaPlayer.isLooping = false
            mMediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}