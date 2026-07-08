package com.example.pftandroidmockproject.domain.model

data class Food(
    val id : Int = 1,
    val name : String,
    val caloriesPerServing : Int,
    val servingDescription : String,
    val isCustom : Boolean = false
)