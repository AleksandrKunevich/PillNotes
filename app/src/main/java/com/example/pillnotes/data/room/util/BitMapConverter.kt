package com.example.pillnotes.data.room.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

object BitMapConverter {

    private const val LIMIT_IMAGE_SIZE = 500000
    private const val QUALITY_DOWN = 20

    @TypeConverter
    fun fromBitMap(bitmap: Bitmap): ByteArray {
        var stream = ByteArrayOutputStream()
        var quality = 100
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        while (stream.toByteArray().size > LIMIT_IMAGE_SIZE) {
            stream = ByteArrayOutputStream()
            quality -= QUALITY_DOWN
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        }
        return stream.toByteArray()
    }

    @TypeConverter
    fun bitmapFromByteArray(imageData: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.size);
    }
}