package com.example.pillnotes.di

import android.content.Context
import com.example.pillnotes.domain.contactdoctor.SaveBitmapImpl
import com.example.pillnotes.domain.contactdoctor.location.LocationService
import com.example.pillnotes.presentation.*
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [AppModule::class]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindContext(context: Context): Builder

        fun build(): ApplicationComponent
    }

    fun inject(target: HomeFragment)
    fun inject(target: NewNoteFragment)
    fun inject(target: ScannerFragment)
    fun inject(target: CalendarFragment)
    fun inject(target: NewsPaperFragment)
    fun inject(target: ContactsFragment)
    fun inject(target: SettingsFragment)
    fun inject(target: NewContactDoctorFragment)
    fun inject(target: SaveBitmapImpl)
    fun inject(target: LocationService)

}
