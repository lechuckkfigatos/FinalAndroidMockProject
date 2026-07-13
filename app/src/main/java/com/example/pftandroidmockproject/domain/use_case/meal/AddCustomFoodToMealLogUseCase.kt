package com.example.pftandroidmockproject.domain.use_case.meal

import com.example.pftandroidmockproject.domain.model.MealType
import com.example.pftandroidmockproject.domain.use_case.food.CreateCustomFoodUseCase
import java.time.LocalDate
import javax.inject.Inject

class AddCustomFoodToMealLogUseCase @Inject constructor(
    private val createCustomFoodUseCase: CreateCustomFoodUseCase,
    private val createMealEntryFromFoodUseCase: CreateMealEntryFromFoodUseCase,
    private val addMealEntryUseCase: AddMealEntryUseCase
) {
    suspend operator fun invoke(
        name: String,
        caloriesPerServing: Int,
        servingDescription: String,
        serving: Double,
        date: LocalDate,
        mealType: MealType
    ) {
        val savedFood = createCustomFoodUseCase(
            name = name,
            caloriesPerServing = caloriesPerServing,
            servingDescription = servingDescription
        )

        val mealEntry = createMealEntryFromFoodUseCase(
            food = savedFood,
            date = date,
            mealType = mealType,
            serving = serving
        )

        addMealEntryUseCase(mealEntry)
    }
}