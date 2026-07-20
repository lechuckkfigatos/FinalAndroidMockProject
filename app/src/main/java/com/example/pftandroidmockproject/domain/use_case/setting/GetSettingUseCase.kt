package com.example.pftandroidmockproject.domain.use_case.setting

import com.example.pftandroidmockproject.domain.model.setting.AppSettings
import com.example.pftandroidmockproject.domain.repository.SettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<AppSettings> {
        return settingsRepository.getSettings()
    }
}