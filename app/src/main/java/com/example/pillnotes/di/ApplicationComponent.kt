package com.example.pillnotes.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context) : Builder

        fun build(): ApplicationComponent
    }

    fun inject()
}
