package com.example.pftandroidmockproject.domain.usecase.meal

import com.example.pftandroidmockproject.domain.model.LocalizedText
import com.example.pftandroidmockproject.domain.model.MealEntry
import com.example.pftandroidmockproject.domain.model.MealType
import com.example.pftandroidmockproject.domain.use_case.meal.CalculateMealCaloriesUseCase
import java.time.LocalDate
import javax.inject.Inject

class CreateCustomMealEntryUseCase @Inject constructor(
    private val calculateMealCaloriesUseCase: CalculateMealCaloriesUseCase
) {

    operator fun invoke(
        foodName: LocalizedText,
        servingDescription: LocalizedText,
        caloriesPerServing: Int,
        serving: Double,
        date: LocalDate,
        mealType: MealType
    ): MealEntry {
        require(foodName.vi.isNotBlank() || foodName.en.isNotBlank()) {
            "Food name must not be blank"
        }

        require(caloriesPerServing > 0) {
            "Calories per serving must be greater than 0"
        }

        require(serving > 0) {
            "Serving must be greater than 0"
        }

        val totalCalories = calculateMealCaloriesUseCase(
            caloriesPerServing = caloriesPerServing,
            serving = serving
        )

        return MealEntry(
            date = date,
            mealType = mealType,
            foodId = null,
            foodNameSnapshot = foodName,
            servingDescriptionSnapshot = servingDescription,
            caloriesPerServingSnapshot = caloriesPerServing,
            serving = serving,
            totalCalories = totalCalories,
            isCustom = true
        )
    }
}