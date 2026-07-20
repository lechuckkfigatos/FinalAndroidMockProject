package com.example.pftandroidmockproject.domain.repository

import com.example.pftandroidmockproject.domain.model.meal.MealEntry
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MealRepository {

    fun getMealEntriesByDate(date: LocalDate): Flow<List<MealEntry>>

    fun getMealEntriesBetweenDates(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<MealEntry>>

    suspend fun addMealEntry(entry: MealEntry)

    suspend fun deleteMealEntry(entryId: Int)
}