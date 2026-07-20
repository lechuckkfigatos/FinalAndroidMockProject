package com.example.pftandroidmockproject.domain.usecase.activity

import com.example.pftandroidmockproject.domain.model.ActivityEntry
import com.example.pftandroidmockproject.domain.model.ActivityType
import com.example.pftandroidmockproject.domain.repository.ActivityRepository
import com.example.pftandroidmockproject.domain.repository.ProfileRepository
import com.example.pftandroidmockproject.domain.use_case.activity.CreateActivityEntryUseCase
import java.time.LocalDate
import javax.inject.Inject

class AddActivityToLogUseCase @Inject constructor(
    private val activityRepository: ActivityRepository,
    private val profileRepository: ProfileRepository,
    private val createActivityEntryUseCase: CreateActivityEntryUseCase
) {
    suspend operator fun invoke(
        activityType: ActivityType,
        durationMinutes: Int,
        date: LocalDate
    ) {
        val profile = profileRepository.getProfileOnce()
            ?: error("Profile is required before adding activity")

        val entry: ActivityEntry = createActivityEntryUseCase(
            activityType = activityType,
            weightKg = profile.weightKg,
            durationMinutes = durationMinutes,
            date = date
        )

        activityRepository.addActivityEntry(entry)
    }
}