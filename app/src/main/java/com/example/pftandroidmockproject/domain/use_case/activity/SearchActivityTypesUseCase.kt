package com.example.pftandroidmockproject.domain.use_case.activity

import com.example.pftandroidmockproject.domain.model.activity.ActivityType
import com.example.pftandroidmockproject.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchActivityTypesUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    operator fun invoke(query: String) : Flow<List<ActivityType>>{
        val cleanQuery = query.trim()

        return if(cleanQuery.isBlank()){
            activityRepository.getAllActivityTypes()
        }
        else{
            activityRepository.searchActivityTypes(query)
        }
    }
}