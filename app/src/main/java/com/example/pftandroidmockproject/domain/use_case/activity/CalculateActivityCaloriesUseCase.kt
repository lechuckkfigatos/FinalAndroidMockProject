package com.example.pftandroidmockproject.domain.use_case.activity

import com.example.pftandroidmockproject.domain.calculator.ActivityCaloriesCalculator
import com.example.pftandroidmockproject.domain.model.activity.ActivityType
import javax.inject.Inject

class CalculateActivityCaloriesUseCase @Inject constructor(
    private val activityCaloriesCalculator: ActivityCaloriesCalculator
) {
    operator fun invoke(
        activityType: ActivityType,
        weightKg : Double,
        durationMinutes: Int
    ): Int {
        return activityCaloriesCalculator.calculateCaloriesBurned(
            metValue = activityType.metValue,
            weightKg = weightKg,
            durationMinutes = durationMinutes
        )
    }
}