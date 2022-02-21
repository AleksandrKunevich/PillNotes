package com.example.pillnotes.domain.util

import android.content.Context
import android.media.MediaPlayer
import com.example.pillnotes.R

class SoundUtils(private val context: Context) {

    companion object {
        private const val LEFT_VOLUME = 0.5f
        private const val RIGHT_VOLUME = 0.5f
    }

    fun playBeep() {
        try {
            val mMediaPlayer = MediaPlayer.create(context, R.raw.beep)
            mMediaPlayer.setVolume(LEFT_VOLUME, RIGHT_VOLUME)
            mMediaPlayer.isLooping = false
            mMediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}