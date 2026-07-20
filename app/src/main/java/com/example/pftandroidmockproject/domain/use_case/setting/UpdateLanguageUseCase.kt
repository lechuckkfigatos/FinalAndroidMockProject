package com.example.pftandroidmockproject.domain.use_case.setting

import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(language: AppLanguage) {
        settingsRepository.updateLanguage(language)
    }
}