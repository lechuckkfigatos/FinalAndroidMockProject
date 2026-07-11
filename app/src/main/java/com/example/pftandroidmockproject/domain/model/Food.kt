package com.example.pftandroidmockproject.domain.model

data class Food(
    val id : Int = 1,
    val name : LocalizedText,
    val caloriesPerServing : Int,
    val servingDescription : LocalizedText,
    val isCustom : Boolean = false
)