package com.example.pftandroidmockproject.domain.use_case.activity

import com.example.pftandroidmockproject.data.local.entity.ActivityTypeEntity
import com.example.pftandroidmockproject.domain.model.activity.ActivityType
import com.example.pftandroidmockproject.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllActivityTypesUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    operator fun invoke() : Flow<List<ActivityType>> {
        return activityRepository.getAllActivityTypes()
    }
}