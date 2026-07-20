package com.example.pftandroidmockproject.presentation.profile

import com.example.pftandroidmockproject.domain.model.activity.ActivityLevel
import com.example.pftandroidmockproject.domain.model.profile.BmiCategory
import com.example.pftandroidmockproject.domain.model.profile.Gender
import com.example.pftandroidmockproject.domain.model.setting.WeightGoal
import java.time.LocalDate

data class ProfileUiState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,

    val fullName: String = "",
    val dateOfBirth: LocalDate? = null,
    val gender: Gender? = null,

    val weightKg: String = "",
    val heightCm: String = "",

    val activityLevel: ActivityLevel? = null,
    val goal: WeightGoal? = null,

    val tdeeCalories: Int? = null,
    val bmiValue: Double? = null,
    val bmiCategory: BmiCategory? = null,

    val hasExistingProfile: Boolean = false
)