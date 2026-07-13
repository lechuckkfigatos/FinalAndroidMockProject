package com.example.pftandroidmockproject.domain.use_case.meal

import com.example.pftandroidmockproject.domain.repository.MealRepository
import javax.inject.Inject

class DeleteMealEntryUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {

    suspend operator fun invoke(entryId: Int) {
        require(entryId > 0) {
            "Meal entry id is invalid"
        }

        mealRepository.deleteMealEntry(entryId)
    }
}