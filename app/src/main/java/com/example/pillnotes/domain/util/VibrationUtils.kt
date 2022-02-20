package com.example.pillnotes.domain.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

private const val VIBRATION_TIME = 200L

class VibrationUtils(private val context: Context) {

    internal fun runVibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibrator =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibrator.defaultVibrator.vibrate(
                VibrationEffect.createOneShot(
                    VIBRATION_TIME,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        VIBRATION_TIME,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }
        }
    }
}