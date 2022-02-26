package com.example.pillnotes.domain.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillnotes.domain.cat.CatInteractorInterface
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class CatViewModel @Inject constructor(
    private val interactor: CatInteractorInterface
) : ViewModel() {

    private val _draw: MutableLiveData<Bitmap?>? = MutableLiveData<Bitmap?>()
    val draw: MutableLiveData<Bitmap?>? get() = _draw

    internal fun getCatRandomBitmap() {
        viewModelScope.launch {
            _draw?.value = interactor.loadCatImage()
        }
    }
}