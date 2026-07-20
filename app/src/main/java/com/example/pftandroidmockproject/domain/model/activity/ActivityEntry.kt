package com.example.pftandroidmockproject.domain.model.activity

import com.example.pftandroidmockproject.domain.model.setting.LocalizedText
import java.time.LocalDate

data class ActivityEntry(
    val id : Int = 0,
    val date : LocalDate,
    val activityTypeId : Int,

    //activity snapshot
    val activityNameSnapshot : LocalizedText,
    val metValueSnapshot: Double,

    val durationMinutes : Int ,
    val caloriesBurned : Int
)
