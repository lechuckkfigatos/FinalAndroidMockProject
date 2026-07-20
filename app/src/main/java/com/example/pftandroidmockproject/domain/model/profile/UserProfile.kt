package com.example.pftandroidmockproject.domain.model.profile

import com.example.pftandroidmockproject.domain.model.activity.ActivityLevel
import com.example.pftandroidmockproject.domain.model.setting.WeightGoal
import java.time.LocalDate

data class UserProfile (
    val id : Int = 1,
    val fullName : String,
    val gender: Gender,
    val dateOfBirth : LocalDate,
    val weightKg : Double,
    val heightCm : Double,
    val activityLevel: ActivityLevel,
    val goal : WeightGoal
)
