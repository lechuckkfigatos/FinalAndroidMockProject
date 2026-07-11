package com.example.pftandroidmockproject.data.repository

import com.example.pftandroidmockproject.data.local.dao.MealEntryDao
import com.example.pftandroidmockproject.data.mapper.toDomain
import com.example.pftandroidmockproject.data.mapper.toEntity
import com.example.pftandroidmockproject.domain.model.MealEntry
import com.example.pftandroidmockproject.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepositoryImpl @Inject constructor(
    private val mealEntryDao: MealEntryDao
) : MealRepository {

    override fun getMealEntriesByDate(
        date: LocalDate
    ): Flow<List<MealEntry>> {
        return mealEntryDao.getMealEntriesByDate(
            dateEpochDay = date.toEpochDay()
        ).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getMealEntriesBetweenDates(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<MealEntry>> {
        return mealEntryDao.getMealEntriesBetweenDates(
            startEpochDay = startDate.toEpochDay(),
            endEpochDay = endDate.toEpochDay()
        ).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addMealEntry(entry: MealEntry) {
        mealEntryDao.insertMealEntry(entry.toEntity())
    }

    override suspend fun deleteMealEntry(entryId: Int) {
        mealEntryDao.deleteMealEntry(entryId)
    }
}