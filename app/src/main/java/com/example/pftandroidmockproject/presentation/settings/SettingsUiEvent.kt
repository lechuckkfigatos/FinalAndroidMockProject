package com.example.pftandroidmockproject.presentation.settings

import com.example.pftandroidmockproject.presentation.common.UiText

sealed interface SettingsUiEvent {
    data class ShowMessage(
        val message: UiText
    ) : SettingsUiEvent
}
