package com.example.pillnotes.di

import android.content.Context
import com.example.pillnotes.presentation.HomeFragment
import dagger.BindsInstance
import dagger.Component

@Component
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindContext(context: Context) : Builder

        fun build(): ApplicationComponent
    }

    fun inject(target: HomeFragment)
}
