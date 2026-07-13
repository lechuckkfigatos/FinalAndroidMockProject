package com.example.pftandroidmockproject.presentation.profile

import com.example.pftandroidmockproject.presentation.common.UiText

sealed interface ProfileUiEvent {

    data class ShowMessage(
        val message : UiText
    ) : ProfileUiEvent

    data object SaveSuccess : ProfileUiEvent
}