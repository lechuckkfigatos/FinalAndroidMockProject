package com.example.pftandroidmockproject.presentation.settings

import com.example.pftandroidmockproject.domain.model.activity.ActivityLevel
import com.example.pftandroidmockproject.domain.model.profile.BmiCategory
import com.example.pftandroidmockproject.domain.model.profile.Gender
import com.example.pftandroidmockproject.domain.model.setting.AppAccentColor
import com.example.pftandroidmockproject.domain.model.setting.AppFontSize
import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode
import com.example.pftandroidmockproject.domain.model.setting.WeightGoal
import java.time.LocalDate

data class SettingsUiState(
    val isLoading: Boolean = false,
    val isProfileSaving: Boolean = false,
    val selectedLanguage: AppLanguage = AppLanguage.VI,
    val selectedThemeMode: AppThemeMode = AppThemeMode.SYSTEM,
    val selectedFontSize: AppFontSize = AppFontSize.MEDIUM,
    val selectedAccentColor: AppAccentColor = AppAccentColor.GREEN,
    val fullName: String = "",
    val dateOfBirth: LocalDate? = null,
    val gender: Gender? = null,
    val weightKg: String = "",
    val heightCm: String = "",
    val bmiValue: Double? = null,
    val bmiCategory: BmiCategory? = null,
    val activityLevel: ActivityLevel? = null,
    val goal: WeightGoal? = null
)
