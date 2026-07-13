package com.example.pftandroidmockproject.domain.use_case.meal

import com.example.pftandroidmockproject.domain.model.Food
import com.example.pftandroidmockproject.domain.model.MealType
import java.time.LocalDate
import javax.inject.Inject

class AddFoodToMealLogUseCase @Inject constructor(
    private val createMealEntryFromFoodUseCase: CreateMealEntryFromFoodUseCase,
    private val addMealEntryUseCase: AddMealEntryUseCase
) {
    suspend operator fun invoke(
        food: Food,
        serving: Double,
        date: LocalDate,
        mealType: MealType
    ) {
        val mealEntry = createMealEntryFromFoodUseCase(
            food = food,
            date = date,
            mealType = mealType,
            serving = serving
        )

        addMealEntryUseCase(mealEntry)
    }
}