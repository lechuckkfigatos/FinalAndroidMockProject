package com.example.pftandroidmockproject.presentation.meal

import com.example.pftandroidmockproject.presentation.common.UiText

sealed interface MealUiEvent {

    data class ShowMessage(
        val message: UiText
    ) : MealUiEvent
}