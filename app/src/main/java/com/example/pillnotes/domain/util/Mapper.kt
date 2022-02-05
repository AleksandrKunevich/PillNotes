package com.example.pillnotes.domain.util

import android.location.Location
import android.location.LocationManager
import com.example.pillnotes.data.room.model.ContactEntity
import com.example.pillnotes.data.room.model.NoteTaskEntity
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.domain.model.NoteTask

internal fun NoteTaskEntity.toNoteTask(): NoteTask {
    return NoteTask(
        uid = uid,
        time = time,
        name = name,
        task = task,
        result = result,
        check = check,
        priority = priority
    )
}

internal fun NoteTask.toNoteTaskEntity(): NoteTaskEntity {
    return NoteTaskEntity(
        uid = uid,
        time = time,
        name = name,
        task = task,
        result = result,
        check = check,
        priority = priority
    )
}

internal fun ContactDoctor.toContactEntity(): ContactEntity {
    return ContactEntity(
        uid = uid,
        name = name,
        profession = profession,
        phone = phone,
        latitude = location.latitude,
        longitude = location.longitude
    )
}

internal fun ContactEntity.toContact(): ContactDoctor {
    return ContactDoctor(
        uid = uid,
        name = name,
        profession = profession,
        phone = phone,
        location = Location(LocationManager.GPS_PROVIDER)
            .apply {
                longitude = this@toContact.longitude
                latitude = this@toContact.latitude
            }
    )
}