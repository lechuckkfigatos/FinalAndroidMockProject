package com.example.pftandroidmockproject.domain.use_case.activity

import com.example.pftandroidmockproject.domain.model.ActivityEntry
import com.example.pftandroidmockproject.domain.repository.ActivityRepository
import javax.inject.Inject

class AddActivityEntryUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke(entry : ActivityEntry) {
        require(entry.durationMinutes > 0){
            "Duration must be greater than 0"
        }
        require(entry.caloriesBurned > 0){
            "Calories burned must be greater than 0"
        }
        activityRepository.addActivityEntry(entry)
    }
}