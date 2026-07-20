package com.example.pftandroidmockproject.domain.use_case.setting


import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode
import com.example.pftandroidmockproject.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateThemeModeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(themeMode: AppThemeMode) {
        settingsRepository.updateThemeMode(themeMode)
    }
}