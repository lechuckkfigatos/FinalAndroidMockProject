package com.example.pftandroidmockproject.presentation.settings

import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode

data class SettingsUiState(
    val isLoading: Boolean = false,
    val selectedLanguage: AppLanguage = AppLanguage.VI,
    val selectedThemeMode: AppThemeMode = AppThemeMode.SYSTEM
)