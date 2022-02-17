package com.example.pillnotes.domain.util

import android.location.Location
import android.location.LocationManager
import com.example.pillnotes.data.room.model.ContactEntity
import com.example.pillnotes.data.room.model.NoteTaskBaseEntity
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.domain.model.NoteTaskBase
import java.util.*

internal fun NoteTaskBaseEntity.toNoteTask(): NoteTaskBase {
    return listOf(
        NoteTaskBase.NoteTime(time = time),
        NoteTaskBase.NoteBody(
            uid = uid,
            title = name,
            task = task,
            result = result,
            check = check,
            priority = priority,
            rrule = rrule
        )
    )[0]
}

internal fun NoteTaskBase.toNoteTaskEntity(): NoteTaskBaseEntity {
    var timeBase = ""
    var uidBase = UUID.randomUUID()
    var nameBase = ""
    var taskBase = ""
    var resultBase = ""
    var checkBase = false
    var priorityBase = 0
    var rruleBase = ""
    when (this) {
        is NoteTaskBase.NoteTime -> {
            timeBase = this.time
        }
        is NoteTaskBase.NoteBody -> {
            uidBase = this.uid
            nameBase = this.title
            taskBase = this.task
            resultBase = this.result.toString()
            checkBase = this.check
            priorityBase = this.priority
            rruleBase = this.rrule
        }
    }
    return NoteTaskBaseEntity(
        time = timeBase,
        uid = uidBase,
        name = nameBase,
        task = taskBase,
        result = resultBase,
        check = checkBase,
        priority = priorityBase,
        rrule = rruleBase
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