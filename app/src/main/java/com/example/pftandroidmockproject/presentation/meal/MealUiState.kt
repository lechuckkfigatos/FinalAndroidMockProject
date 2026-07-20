package com.example.pftandroidmockproject.presentation.meal

import com.example.pftandroidmockproject.domain.model.meal.Food
import com.example.pftandroidmockproject.domain.model.meal.MealEntry
import com.example.pftandroidmockproject.domain.model.meal.MealType
import java.time.LocalDate

data class MealUiState (
    val isLoading : Boolean = false,
    val selectedDate : LocalDate = LocalDate.now(),
    val selectedMealType : MealType = MealType.BREAKFAST,

    val mealEntries : List<MealEntry> = emptyList(),

    val searchQuery : String = "",
    val searchResults : List<Food> = emptyList(),
    val selectedFood : Food? = null,
    val selectedFoodServing : String = "1",

    val customFoodName : String = "",
    val customCaloriesPerServing: String = "",
    val customServingDescription: String = "",
    val customServing: String = "1"
){
    val totalDayCalories: Int
        get() = mealEntries.sumOf { it.totalCalories }

    val selectedMealEntries : List<MealEntry>
        get() = mealEntries.filter { it.mealType == selectedMealType }

    val selectedMealTotalCalories: Int
        get() = selectedMealEntries.sumOf { it.totalCalories }
}