package com.example.pftandroidmockproject.data.mapper

import com.example.pftandroidmockproject.data.local.entity.ActivityTypeEntity
import com.example.pftandroidmockproject.domain.model.ActivityType

fun ActivityTypeEntity.toDomain(): ActivityType {
    return ActivityType(
        id = id,
        name = name,
        metValue = metValue
    )
}

fun ActivityType.toEntity(): ActivityTypeEntity {
    return ActivityTypeEntity(
        id = id,
        name = name,
        metValue = metValue
    )
}