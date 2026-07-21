package com.example.pftandroidmockproject.domain.repository


import com.example.pftandroidmockproject.domain.model.setting.AppFontSize
import com.example.pftandroidmockproject.domain.model.setting.AppAccentColor
import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.model.setting.AppSettings
import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettings(): Flow<AppSettings>

    suspend fun updateLanguage(language: AppLanguage)

    suspend fun updateThemeMode(themeMode: AppThemeMode)

    suspend fun updateFontSize(fontSize: AppFontSize)

    suspend fun updateAccentColor(accentColor: AppAccentColor)
}
