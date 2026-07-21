package com.example.pftandroidmockproject.presentation.navigation

import com.example.pftandroidmockproject.domain.model.setting.AppAccentColor
import com.example.pftandroidmockproject.domain.model.setting.AppFontSize
import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode

data class AppStartUiState(
    val isLoading: Boolean = true,
    val startDestination: String? = null,
    val language: AppLanguage = AppLanguage.VI,
    val themeMode: AppThemeMode = AppThemeMode.SYSTEM,
    val fontSize: AppFontSize = AppFontSize.MEDIUM,
    val accentColor: AppAccentColor = AppAccentColor.GREEN
)
