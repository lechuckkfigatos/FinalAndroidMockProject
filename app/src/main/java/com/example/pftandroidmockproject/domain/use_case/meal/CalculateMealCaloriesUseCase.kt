package com.example.pftandroidmockproject.domain.use_case.meal

import com.example.pftandroidmockproject.domain.calculator.MealCaloriesCalculator
import com.example.pftandroidmockproject.domain.model.Food
import javax.inject.Inject

class CalculateMealCaloriesUseCase @Inject constructor(
    val mealCaloriesCalculator: MealCaloriesCalculator
) {
    operator fun invoke(
        food: Food,
        serving : Double
    ): Int{
        return mealCaloriesCalculator.calculateMealCalories(
            caloriesPerServing = food.caloriesPerServing,
            serving = serving
        )
    }
}