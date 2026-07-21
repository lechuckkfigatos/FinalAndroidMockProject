package com.example.pftandroidmockproject.presentation.activity.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.example.pftandroidmockproject.domain.model.setting.LocalizedText

@Composable
fun LocalizedText.displayText(): String {
    val language = LocalConfiguration
        .current
        .locales[0]
        .language

    return if (language == "vi") {
        vi
    } else {
        en
    }
}
