package com.example.pillnotes

import android.app.Application
import com.example.pillnotes.di.ApplicationComponent
import com.example.pillnotes.di.DaggerAppComponent

class PillNotesApplication : Application() {

    companion object {
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .context(context = this)
            .build()
    }
}