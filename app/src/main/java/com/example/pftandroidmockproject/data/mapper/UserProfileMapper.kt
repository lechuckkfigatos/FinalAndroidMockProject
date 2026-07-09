package com.example.pftandroidmockproject.data.mapper

import com.example.pftandroidmockproject.data.local.entity.UserProfileEntity
import com.example.pftandroidmockproject.domain.model.ActivityLevel
import com.example.pftandroidmockproject.domain.model.Gender
import com.example.pftandroidmockproject.domain.model.UserProfile
import com.example.pftandroidmockproject.domain.model.WeightGoal
import java.time.LocalDate

fun UserProfileEntity.toDomain(): UserProfile{
    return UserProfile(
        id = id,
        fullName = fullName,
        gender = Gender.valueOf(gender),
        dateOfBirth = LocalDate.ofEpochDay(dateOfBirth),
        weightKg = weightKg,
        heightCm = heightCm,
        activityLevel = ActivityLevel.valueOf(activityLevel),
        goal = WeightGoal.valueOf(goal)
    )
}

fun UserProfile.toEntity(): UserProfileEntity{
    return UserProfileEntity(
        id = id,
        fullName = fullName,
        gender = gender.name,
        dateOfBirth = dateOfBirth.toEpochDay(),
        heightCm = heightCm,
        weightKg = weightKg,
        activityLevel = activityLevel.name,
        goal = goal.name
    )
}