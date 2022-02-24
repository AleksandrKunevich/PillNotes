package com.example.pillnotes.domain.cat

import android.graphics.Bitmap

interface CatInteractorInterface {

    suspend fun loadCatImage(): Bitmap?
}