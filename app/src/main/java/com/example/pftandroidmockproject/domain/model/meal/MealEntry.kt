package com.example.pftandroidmockproject.domain.model.meal

import com.example.pftandroidmockproject.domain.model.setting.LocalizedText
import java.time.LocalDate

data class MealEntry (
    val id : Int = 0,
    val date : LocalDate,
    val mealType: MealType,

    val foodId: Int,

    //meal snapshot
    val foodNameSnapshot : LocalizedText,
    val servingDescriptionSnapshot : LocalizedText,
    val caloriesPerServingSnapshot: Int,

    val serving: Double,
    val totalCalories : Int
)
