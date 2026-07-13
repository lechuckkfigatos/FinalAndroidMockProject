package com.example.pftandroidmockproject.domain.use_case.activity

import com.example.pftandroidmockproject.domain.model.ActivityType
import java.time.LocalDate
import javax.inject.Inject

class AddActivityToLogUseCase @Inject constructor(
    private val createActivityEntryUseCase: CreateActivityEntryUseCase,
    private val addActivityEntryUseCase: AddActivityEntryUseCase
) {

    suspend operator fun invoke(
        activityType: ActivityType,
        weightKg: Double,
        durationMinutes: Int,
        date: LocalDate
    ) {
        val activityEntry = createActivityEntryUseCase(
            activityType = activityType,
            weightKg = weightKg,
            durationMinutes = durationMinutes,
            date = date
        )

        addActivityEntryUseCase(activityEntry)
    }
}