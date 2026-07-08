package com.example.pftandroidmockproject.domain.use_case.meal

import com.example.pftandroidmockproject.domain.model.Food
import com.example.pftandroidmockproject.domain.model.MealEntry
import com.example.pftandroidmockproject.domain.model.MealType
import java.time.LocalDate
import javax.inject.Inject

class CreateMealEntryUseCase @Inject constructor(
    val calculateMealCaloriesUseCase: CalculateMealCaloriesUseCase
) {

    operator fun invoke(
      food : Food,
      date : LocalDate,
      mealType: MealType,
      serving : Double
    ): MealEntry{
        require(serving > 0){
            "Serving must be greater than 0"
        }

        val totalCalories = calculateMealCaloriesUseCase(
            food = food,
            serving = serving
        )

        return MealEntry(
            id = food.id,
            date = LocalDate.now(),
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