package com.example.pftandroidmockproject.data.repository

import com.example.pftandroidmockproject.data.local.dao.ActivityEntryDao
import com.example.pftandroidmockproject.data.local.dao.ActivityTypeDao
import com.example.pftandroidmockproject.data.local.dao.MealEntryDao
import com.example.pftandroidmockproject.data.mapper.toDomain
import com.example.pftandroidmockproject.data.mapper.toEntity
import com.example.pftandroidmockproject.domain.model.ActivityEntry
import com.example.pftandroidmockproject.domain.model.ActivityType
import com.example.pftandroidmockproject.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityRepositoryImpl @Inject constructor(
    private val activityTypeDao: ActivityTypeDao,
    private val activityEntryDao: ActivityEntryDao
) : ActivityRepository{
    override fun getAllActivityTypes(): Flow<List<ActivityType>> {
        return activityTypeDao.getAllActivityTypes()
            .map { entities -> entities
                .map { it.toDomain() }}
    }

    override fun searchActivityTypes(query: String): Flow<List<ActivityType>> {
        return activityTypeDao.searchActivityTypes(query)
            .map { entities -> entities
                .map { it.toDomain() }}
    }

    override fun getActivityEntriesByDate(date: LocalDate): Flow<List<ActivityEntry>> {
        return activityEntryDao.getActivityEntriesByDate(date.toEpochDay())
            .map { entities -> entities
                .map { it.toDomain() }}
    }

    override fun getActivityEntriesBetweenDates(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<ActivityEntry>> {
        return  activityEntryDao.getActivityEntriesBetweenDates(
            startDate.toEpochDay(),
            endDate.toEpochDay())
            .map {entities -> entities
                .map { it.toDomain() }}
    }

    override suspend fun addActivityEntry(entry: ActivityEntry) {
        return activityEntryDao.insertActivityEntry(entry.toEntity())
    }

    override suspend fun deleteActivityEntry(entryId: Int) {
        return activityEntryDao.deleteActivityEntry(entryId)
    }
}