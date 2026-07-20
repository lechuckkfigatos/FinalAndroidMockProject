package com.example.pftandroidmockproject.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.model.setting.AppSettings
import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(
    name = "settings_preferences"
)

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object Keys {
        val LANGUAGE = stringPreferencesKey("language")
        val THEME_MODE = stringPreferencesKey("theme_mode")
    }

    val settings: Flow<AppSettings> =
        context.settingsDataStore.data.map { preferences ->
            val language = preferences[Keys.LANGUAGE]
                ?.let { value ->
                    runCatching {
                        AppLanguage.valueOf(value)
                    }.getOrNull()
                }
                ?: AppLanguage.VI

            val themeMode = preferences[Keys.THEME_MODE]
                ?.let { value ->
                    runCatching {
                        AppThemeMode.valueOf(value)
                    }.getOrNull()
                }
                ?: AppThemeMode.SYSTEM

            AppSettings(
                language = language,
                themeMode = themeMode
            )
        }

    suspend fun updateLanguage(language: AppLanguage) {
        context.settingsDataStore.edit { preferences ->
            preferences[Keys.LANGUAGE] = language.name
        }
    }

    suspend fun updateThemeMode(themeMode: AppThemeMode) {
        context.settingsDataStore.edit { preferences ->
            preferences[Keys.THEME_MODE] = themeMode.name
        }
    }
}