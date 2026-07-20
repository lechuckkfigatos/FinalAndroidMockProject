package com.example.pftandroidmockproject.domain.model.setting

data class AppSettings(
    val language: AppLanguage = AppLanguage.VI,
    val themeMode: AppThemeMode = AppThemeMode.SYSTEM
)