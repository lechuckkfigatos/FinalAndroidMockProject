package com.example.pftandroidmockproject.presentation.activity

import com.example.pftandroidmockproject.presentation.common.UiText

sealed interface ActivityUiEvent {
    data class ShowMessage(
        val message : UiText
    ): ActivityUiEvent
}