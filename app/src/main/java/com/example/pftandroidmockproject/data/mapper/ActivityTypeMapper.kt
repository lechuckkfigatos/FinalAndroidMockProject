package com.example.pftandroidmockproject.data.mapper

import com.example.pftandroidmockproject.data.local.entity.ActivityTypeEntity
import com.example.pftandroidmockproject.domain.model.activity.ActivityType
import com.example.pftandroidmockproject.domain.model.setting.LocalizedText

fun ActivityTypeEntity.toDomain(): ActivityType {
    return ActivityType(
        id = id,
        name = LocalizedText(
            vi = nameVi,
            en = nameEn
        ),
        metValue = metValue
    )
}

fun ActivityType.toEntity(): ActivityTypeEntity {
    return ActivityTypeEntity(
        id = id,
        nameEn = name.en,
        nameVi = name.vi,
        metValue = metValue
    )
}
