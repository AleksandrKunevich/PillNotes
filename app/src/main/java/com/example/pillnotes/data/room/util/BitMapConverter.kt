package com.example.pillnotes.data.room.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

private const val LIMIT_IMAGE_SIZE = 500000

object BitMapConverter {
    @TypeConverter
    fun fromBitMap(bitmap: Bitmap): ByteArray {
        var stream = ByteArrayOutputStream()
        var cality = 100
        bitmap.compress(Bitmap.CompressFormat.JPEG, cality, stream)
        while (stream.toByteArray().size > LIMIT_IMAGE_SIZE) {
            stream = ByteArrayOutputStream()
            cality -= 20
            bitmap.compress(Bitmap.CompressFormat.JPEG, cality, stream)
        }
        return stream.toByteArray()
    }

    @TypeConverter
    fun bitmapFromByteArray(imageData: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.size);
    }
}