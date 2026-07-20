package com.example.pftandroidmockproject.domain.use_case.food

import com.example.pftandroidmockproject.domain.model.meal.Food
import com.example.pftandroidmockproject.domain.repository.FoodRepository
import javax.inject.Inject

class UpdateFoodUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {

    suspend operator fun invoke(food: Food) {
        require(food.id > 0) {
            "Food id is invalid"
        }

        require(food.name.vi.isNotBlank()) {
            "Vietnamese food name must not be blank"
        }

        require(food.name.en.isNotBlank()) {
            "English food name must not be blank"
        }

        require(food.caloriesPerServing > 0) {
            "Calories per serving must be greater than 0"
        }

        require(food.servingDescription.vi.isNotBlank()) {
            "Vietnamese serving description must not be blank"
        }

        require(food.servingDescription.en.isNotBlank()) {
            "English serving description must not be blank"
        }

        foodRepository.updateFood(food)
    }
}