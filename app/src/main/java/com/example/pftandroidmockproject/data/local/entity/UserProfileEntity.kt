package com.example.pftandroidmockproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey
    val id : Int = 1,

    val fullName : String,
    val gender : String,
    val dateOfBirth : Long,

    val heightCm : Double,
    val weightKg : Double,

    val activityLevel : String,
    val goal : String
)