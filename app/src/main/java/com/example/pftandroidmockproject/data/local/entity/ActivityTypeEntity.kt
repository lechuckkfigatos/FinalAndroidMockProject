package com.example.pftandroidmockproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_types")
data class ActivityTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    val nameEn : String,
    val nameVi : String,

    val metValue : Double,
)
