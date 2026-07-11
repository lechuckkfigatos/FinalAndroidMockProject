package com.example.pftandroidmockproject.data.mapper

import com.example.pftandroidmockproject.data.local.entity.ActivityEntryEntity
import com.example.pftandroidmockproject.domain.model.ActivityEntry
import com.example.pftandroidmockproject.domain.model.LocalizedText
import java.time.LocalDate

fun ActivityEntryEntity.toDomain() : ActivityEntry {
    return ActivityEntry(
        id = id,
        date = LocalDate.ofEpochDay(date),
        activityTypeId = activityTypeId,

        activityNameSnapshot = LocalizedText(
            vi = activityNameSnapshotVi,
            en = activityNameSnapshotEn),

        metValueSnapshot = metValueSnapshot,
        durationMinutes = durationMinutes,
        caloriesBurned =caloriesBurned
    )
}

fun ActivityEntry.toEntity(): ActivityEntryEntity {
    return ActivityEntryEntity(
        id = id,
        date = date.toEpochDay(),
        activityTypeId = activityTypeId,

        activityNameSnapshotEn = activityNameSnapshot.en,
        activityNameSnapshotVi = activityNameSnapshot.vi,

        metValueSnapshot = metValueSnapshot,
        durationMinutes = durationMinutes,
        caloriesBurned = caloriesBurned
    )
}