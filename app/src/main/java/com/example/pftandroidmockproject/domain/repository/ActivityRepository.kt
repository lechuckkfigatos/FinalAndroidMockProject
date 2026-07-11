package com.example.pftandroidmockproject.domain.repository

import com.example.pftandroidmockproject.domain.model.ActivityEntry
import com.example.pftandroidmockproject.domain.model.ActivityType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ActivityRepository {

    fun getAllActivityTypes(): Flow<List<ActivityType>>

    fun searchActivityTypes(query: String): Flow<List<ActivityType>>

    fun getActivityEntriesByDate(date: LocalDate): Flow<List<ActivityEntry>>

    fun getActivityEntriesBetweenDates(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<ActivityEntry>>

    suspend fun addActivityEntry(entry: ActivityEntry)

    suspend fun deleteActivityEntry(entryId: Int)
}