package com.example.pftandroidmockproject.domain.use_case.dashboard

import com.example.pftandroidmockproject.domain.model.statistic.DailyCalorieSummary
import com.example.pftandroidmockproject.domain.repository.ActivityRepository
import com.example.pftandroidmockproject.domain.repository.MealRepository
import com.example.pftandroidmockproject.domain.repository.ProfileRepository
import com.example.pftandroidmockproject.domain.use_case.profile.CalculateTdeeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class GetDailyDashboardUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val mealRepository: MealRepository,
    private val activityRepository: ActivityRepository,
    private val calculateTdeeUseCase: CalculateTdeeUseCase,
    private val calculateDailySummaryUseCase: CalculateDailySummaryUseCase
) {

    operator fun invoke(date: LocalDate): Flow<DailyCalorieSummary?> {
        return combine(
            profileRepository.getProfile(),
            mealRepository.getMealEntriesByDate(date),
            activityRepository.getActivityEntriesByDate(date)
        ) { profile, meals, activities ->

            if (profile == null) {
                null
            } else {
                val targetCalories = calculateTdeeUseCase(profile)

                calculateDailySummaryUseCase(
                    targetCalories = targetCalories,
                    meals = meals,
                    activities = activities
                )
            }
        }
    }
}