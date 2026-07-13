package com.example.pftandroidmockproject.domain.use_case.meal

import com.example.pftandroidmockproject.domain.model.MealEntry
import com.example.pftandroidmockproject.domain.repository.MealRepository
import javax.inject.Inject

class AddMealEntryUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(entry: MealEntry) {
        mealRepository.addMealEntry(entry)
    }
}