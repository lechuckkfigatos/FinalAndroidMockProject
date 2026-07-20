package com.example.pftandroidmockproject.domain.use_case.food

import com.example.pftandroidmockproject.domain.model.Food
import com.example.pftandroidmockproject.domain.model.LocalizedText
import com.example.pftandroidmockproject.domain.repository.FoodRepository
import javax.inject.Inject

class CreateCustomFoodUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {

    suspend operator fun invoke(
        name: String,
        caloriesPerServing: Int,
        servingDescription: String
    ): Food {
        val cleanName = name.trim()
        val cleanServingDescription = servingDescription.trim()

        require(cleanName.isNotBlank()) {
            "Food name must not be blank"
        }

        require(caloriesPerServing > 0) {
            "Calories per serving must be greater than 0"
        }

        require(cleanServingDescription.isNotBlank()) {
            "Serving description must not be blank"
        }

        val customFood = Food(
            id = 0,
            name = LocalizedText(
                vi = cleanName,
                en = cleanName
            ),
            caloriesPerServing = caloriesPerServing,
            servingDescription = LocalizedText(
                vi = cleanServingDescription,
                en = cleanServingDescription
            ),
            isCustom = true
        )

        return foodRepository.addFood(customFood)
    }
}