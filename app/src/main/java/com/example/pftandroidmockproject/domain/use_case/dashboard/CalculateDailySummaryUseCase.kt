package com.example.pftandroidmockproject.domain.use_case.dashboard

import com.example.pftandroidmockproject.domain.model.ActivityEntry
import com.example.pftandroidmockproject.domain.model.DailyCalorieSummary
import com.example.pftandroidmockproject.domain.model.MealEntry
import javax.inject.Inject

class CalculateDailySummaryUseCase @Inject constructor() {

    operator fun invoke(
        targetCalories: Int,
        meals : List<MealEntry>,
        activities: List<ActivityEntry>
    ) : DailyCalorieSummary{

        val totalIntakeCalories = meals.sumOf { it.totalCalories }

        val totalBurnedCalories = activities.sumOf { it.caloriesBurned }

        val netCalories = totalIntakeCalories - totalBurnedCalories

        val remainingCalories = targetCalories - netCalories

        val intakeProgress = if (targetCalories > 0) {
            totalIntakeCalories.toFloat() / targetCalories.toFloat()
        } else {
            0f
        }

        return DailyCalorieSummary(
            targetCalorie = targetCalories,
            totalIntakeCalories = totalIntakeCalories,
            totalBurnedCalories = totalBurnedCalories,
            netCalories = netCalories,
            remainingCalories = remainingCalories,
            intakeProgress = intakeProgress,
            isOverTarget = remainingCalories < 0
        )
    }
}