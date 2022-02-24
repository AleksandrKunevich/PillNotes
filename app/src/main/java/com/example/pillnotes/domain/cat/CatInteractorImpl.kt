package com.example.pillnotes.domain.cat

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.pillnotes.data.retrofit.CatApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatInteractorImpl @Inject constructor(
    private val context: Context,
    private val catApi: CatApi
) : CatInteractorInterface {

    private var result: Bitmap? = null

    override suspend fun loadCatImage(): Bitmap {
        val bitmap = withContext(Dispatchers.IO) {
            val catUrl = async {
                return@async catApi.getCatRandom()[0].url
            }.await()

            val waitBitMap = async {
                Glide.with(context)
                    .asBitmap()
                    .load(catUrl)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            result = resource
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
                while (true) {
                    if (result != null) return@async result
                }
            }.await()
            return@withContext waitBitMap
        }
        return bitmap as Bitmap
    }
}