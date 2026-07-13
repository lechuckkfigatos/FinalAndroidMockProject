package com.example.pftandroidmockproject.domain.use_case.activity

import com.example.pftandroidmockproject.domain.model.ActivityEntry
import com.example.pftandroidmockproject.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetActivityEntriesByDateUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    operator fun invoke(date: LocalDate) : Flow<List<ActivityEntry>>  {
        return activityRepository.getActivityEntriesByDate(date)
    }
}