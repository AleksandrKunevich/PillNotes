package com.example.pillnotes.domain.contactdoctor

import android.content.Context
import android.graphics.Bitmap

interface SaveBitmapInterface {

    fun saveBitmapInStorage(bitmap: Bitmap, context: Context)
}