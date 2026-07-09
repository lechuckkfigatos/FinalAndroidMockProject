package com.example.pftandroidmockproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_entries")
data class ActivityEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val date: Long,
    val activityTypeId : Int,

    val activityNameSnapshot: String,
    val metValueSnapshot: Double,

    val durationMinutes: Int,
    val caloriesBurned: Int
)
