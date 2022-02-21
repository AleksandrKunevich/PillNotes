package com.example.pillnotes.domain.util

import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentScannerBinding

class FlashLightUtils() {

    internal fun toggleFlashLight(status: Boolean, view: FragmentScannerBinding) {
        try {
            if (status) {
                view.codeScannerView.setTorchOn()
                view.flashButton.setImageResource(R.drawable.ic_flash_on_24)
            } else {
                view.codeScannerView.setTorchOff()
                view.flashButton.setImageResource(R.drawable.ic_flash_off_24)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}