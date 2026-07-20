package com.example.pftandroidmockproject.domain.repository


import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.model.setting.AppSettings
import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettings(): Flow<AppSettings>

    suspend fun updateLanguage(language: AppLanguage)

    suspend fun updateThemeMode(themeMode: AppThemeMode)
}