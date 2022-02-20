package com.example.pillnotes.domain.util.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.pillnotes.domain.util.SoundUtils
import com.example.pillnotes.domain.util.VibrationUtils
import dagger.Module
import dagger.Provides

@Module
object UtilsModule {

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    fun provideVibrationUtils(context: Context): VibrationUtils = VibrationUtils(context)

    @Provides
    fun provideSoundUtils(context: Context): SoundUtils = SoundUtils(context)
}