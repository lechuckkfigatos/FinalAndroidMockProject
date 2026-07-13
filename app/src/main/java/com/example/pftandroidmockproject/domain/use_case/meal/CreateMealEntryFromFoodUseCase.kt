package com.example.pftandroidmockproject.domain.use_case.meal

import com.example.pftandroidmockproject.domain.model.Food
import com.example.pftandroidmockproject.domain.model.MealEntry
import com.example.pftandroidmockproject.domain.model.MealType
import java.time.LocalDate
import javax.inject.Inject

class CreateMealEntryFromFoodUseCase @Inject constructor(
    private val calculateMealCaloriesUseCase: CalculateMealCaloriesUseCase
) {
    operator fun invoke(
        food: Food,
        date: LocalDate,
        mealType: MealType,
        serving: Double
    ): MealEntry {
        require(food.id > 0)
        require(serving > 0)

        val totalCalories = calculateMealCaloriesUseCase(
            caloriesPerServing = food.caloriesPerServing,
            serving = serving
        )

        return MealEntry(
            date = date,
            mealType = mealType,
            foodId = food.id,
            foodNameSnapshot = food.name,
            servingDescriptionSnapshot = food.servingDescription,
            caloriesPerServingSnapshot = food.caloriesPerServing,
            serving = serving,
            totalCalories = totalCalories
        )
    }
}