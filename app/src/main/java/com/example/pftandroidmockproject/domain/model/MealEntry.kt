package com.example.pftandroidmockproject.domain.model

import java.time.LocalDate

data class MealEntry (
    val id : Int = 1,
    val date : LocalDate,
    val mealType: MealType,

    val foodId: Int,

    //meal snapshot
    val foodNameSnapshot : String,
    val servingDescriptionSnapshot : String,
    val caloriesPerServingSnapshot: Int,

    val serving: Double,
    val totalCalories : Int
)