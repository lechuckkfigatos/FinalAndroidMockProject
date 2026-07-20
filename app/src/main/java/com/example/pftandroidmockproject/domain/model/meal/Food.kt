package com.example.pftandroidmockproject.domain.model.meal

import com.example.pftandroidmockproject.domain.model.setting.LocalizedText

data class Food(
    val id : Int = 1,
    val name : LocalizedText,
    val caloriesPerServing : Int,
    val servingDescription : LocalizedText,
    val isCustom : Boolean = false
)
