package com.example.pftandroidmockproject.domain.use_case.activity

import com.example.pftandroidmockproject.domain.model.ActivityEntry
import com.example.pftandroidmockproject.domain.model.ActivityType
import java.time.LocalDate
import javax.inject.Inject

class CreateActivityEntryUseCase @Inject constructor(
    private val calculateActivityCaloriesUseCase: CalculateActivityCaloriesUseCase
){

    operator fun invoke(
        activityType: ActivityType,
        weightKg : Double,
        durationMinutes: Int,
        date : LocalDate
    ) : ActivityEntry{
        require(weightKg > 0){
            "Weight must be greater than 0 kg"
        }
        require(durationMinutes > 0){
            "Duration must be greater than 0 minutes"
        }

        val caloriesBurned = calculateActivityCaloriesUseCase(
            activityType = activityType,
            weightKg = weightKg,
            durationMinutes = durationMinutes
        )

        return ActivityEntry(
            date = date,

            activityTypeId = activityType.id,

            activityNameSnapshot = activityType.name,
            metValueSnapshot = activityType.metValue,

            durationMinutes = durationMinutes,
            caloriesBurned = caloriesBurned
        )
    }
}