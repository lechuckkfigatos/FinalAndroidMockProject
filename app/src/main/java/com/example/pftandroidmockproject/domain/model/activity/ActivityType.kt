package com.example.pftandroidmockproject.domain.model.activity

import com.example.pftandroidmockproject.domain.model.setting.LocalizedText

data class ActivityType (
    val id : Int = 1,
    val name : LocalizedText,
    val metValue : Double
)
