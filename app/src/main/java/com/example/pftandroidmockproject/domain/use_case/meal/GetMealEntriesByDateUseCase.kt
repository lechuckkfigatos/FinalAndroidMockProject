package com.example.pftandroidmockproject.domain.use_case.meal

import com.example.pftandroidmockproject.domain.model.meal.MealEntry
import com.example.pftandroidmockproject.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetMealEntriesByDateUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<MealEntry>> {
        return mealRepository.getMealEntriesByDate(date)
    }
}