package com.example.pftandroidmockproject.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pftandroidmockproject.domain.model.setting.AppAccentColor
import com.example.pftandroidmockproject.domain.model.setting.AppFontSize
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
        val FONT_SIZE = stringPreferencesKey("font_size")
        val ACCENT_COLOR = stringPreferencesKey("accent_color")
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

            val fontSize = preferences[Keys.FONT_SIZE]
                ?.let { value ->
                    runCatching {
                        AppFontSize.valueOf(value)
                    }.getOrNull()
                }
                ?: AppFontSize.MEDIUM

            val accentColor = preferences[Keys.ACCENT_COLOR]
                ?.let { value ->
                    runCatching {
                        AppAccentColor.valueOf(value)
                    }.getOrNull()
                }
                ?: AppAccentColor.GREEN

            AppSettings(
                language = language,
                themeMode = themeMode,
                fontSize = fontSize,
                accentColor = accentColor
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

    suspend fun updateFontSize(fontSize: AppFontSize) {
        context.settingsDataStore.edit { preferences ->
            preferences[Keys.FONT_SIZE] = fontSize.name
        }
    }

    suspend fun updateAccentColor(accentColor: AppAccentColor) {
        context.settingsDataStore.edit { preferences ->
            preferences[Keys.ACCENT_COLOR] = accentColor.name
        }
    }
}
