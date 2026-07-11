package com.example.pftandroidmockproject.domain.model

import java.time.LocalDate

data class MealEntry (
    val id : Int = 1,
    val date : LocalDate,
    val mealType: MealType,

    // null nếu user tự nhập
    val foodId: Int?,

    //meal snapshot
    val foodNameSnapshot : LocalizedText,
    val servingDescriptionSnapshot : LocalizedText,
    val caloriesPerServingSnapshot: Int,

    val serving: Double,
    val totalCalories : Int,

    val isCustom : Boolean
)