package com.example.pftandroidmockproject.domain.use_case.statistic

import com.example.pftandroidmockproject.domain.calculator.TdeeCalculator
import com.example.pftandroidmockproject.domain.model.statistic.DailyStatistic
import com.example.pftandroidmockproject.domain.repository.ActivityRepository
import com.example.pftandroidmockproject.domain.repository.MealRepository
import com.example.pftandroidmockproject.domain.repository.ProfileRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetWeeklyStatisticsUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val mealRepository: MealRepository,
    private val activityRepository: ActivityRepository,
    private val tdeeCalculator: TdeeCalculator
) {
    operator fun invoke(
        endDate: LocalDate
    ): Flow<List<DailyStatistic>> {
        val startDate = endDate.minusDays(6)

        return combine(
            profileRepository.getProfile(),
            mealRepository.getMealEntriesBetweenDates(startDate, endDate),
            activityRepository.getActivityEntriesBetweenDates(startDate, endDate)
        ) { profile, mealEntries, activityEntries ->
            if (profile == null) {
                emptyList()
            } else {
                val targetCalories = tdeeCalculator.calculateTdee(profile)

                val dates = (0L..6L).map { offset ->
                    startDate.plusDays(offset)
                }

                dates.map { date ->
                    val dailyMeals = mealEntries.filter { entry ->
                        entry.date == date
                    }

                    val dailyActivities = activityEntries.filter { entry ->
                        entry.date == date
                    }

                    val intakeCalories = dailyMeals.sumOf { entry ->
                        entry.totalCalories
                    }

                    val burnedCalories = dailyActivities.sumOf { entry ->
                        entry.caloriesBurned
                    }

                    val netCalories = intakeCalories - burnedCalories
                    val remainingCalories = targetCalories - netCalories

                    DailyStatistic(
                        date = date,
                        targetCalories = targetCalories,
                        intakeCalories = intakeCalories,
                        burnedCalories = burnedCalories,
                        netCalories = netCalories,
                        remainingCalories = remainingCalories
                    )
                }
            }
        }
    }
}