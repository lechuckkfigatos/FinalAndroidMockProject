package com.example.pftandroidmockproject.domain.calculator

import jakarta.inject.Inject
import kotlin.math.roundToInt

class MealCaloriesCalculator @Inject constructor() {

    fun calculateMealCalories(
        caloriesPerServing: Int,
        serving: Double
    ): Int {
        return (caloriesPerServing * serving).roundToInt()
    }
}
