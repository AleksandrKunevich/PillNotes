package com.example.pillnotes.data.di

import com.example.pillnotes.data.room.di.RoomModule
import dagger.Module

@Module(
    includes = [
        RoomModule::class
    ]
)
interface DataModule
