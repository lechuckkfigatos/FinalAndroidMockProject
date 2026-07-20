package com.example.pftandroidmockproject.data.repository

import com.example.pftandroidmockproject.data.local.datastore.SettingsDataStore
import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.model.setting.AppSettings
import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode

import com.example.pftandroidmockproject.domain.repository.SettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingsRepository {

    override fun getSettings(): Flow<AppSettings> {
        return settingsDataStore.settings
    }

    override suspend fun updateLanguage(language: AppLanguage) {
        settingsDataStore.updateLanguage(language)
    }

    override suspend fun updateThemeMode(themeMode: AppThemeMode) {
        settingsDataStore.updateThemeMode(themeMode)
    }
}