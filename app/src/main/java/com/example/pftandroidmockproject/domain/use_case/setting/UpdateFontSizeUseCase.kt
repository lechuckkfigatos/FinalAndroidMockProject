package com.example.pftandroidmockproject.domain.use_case.setting

import com.example.pftandroidmockproject.domain.model.setting.AppFontSize
import com.example.pftandroidmockproject.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateFontSizeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(fontSize: AppFontSize) {
        settingsRepository.updateFontSize(fontSize)
    }
}
