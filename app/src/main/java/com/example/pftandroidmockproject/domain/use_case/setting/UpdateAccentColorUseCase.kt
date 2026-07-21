package com.example.pftandroidmockproject.domain.use_case.setting

import com.example.pftandroidmockproject.domain.model.setting.AppAccentColor
import com.example.pftandroidmockproject.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateAccentColorUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(accentColor: AppAccentColor) {
        settingsRepository.updateAccentColor(accentColor)
    }
}
