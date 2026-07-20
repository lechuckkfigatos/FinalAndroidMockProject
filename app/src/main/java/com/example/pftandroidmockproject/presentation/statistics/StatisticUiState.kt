package com.example.pftandroidmockproject.presentation.statistics

import com.example.pftandroidmockproject.domain.model.statistic.DailyStatistic
import java.time.LocalDate

data class StatisticsUiState(
    val isLoading: Boolean = false,
    val endDate: LocalDate = LocalDate.now(),
    val dailyStatistics: List<DailyStatistic> = emptyList(),
    val errorMessage: String? = null
) {
    val totalIntakeCalories: Int
        get() = dailyStatistics.sumOf { it.intakeCalories }

    val totalBurnedCalories: Int
        get() = dailyStatistics.sumOf { it.burnedCalories }

    val averageIntakeCalories: Int
        get() = if (dailyStatistics.isEmpty()) {
            0
        } else {
            totalIntakeCalories / dailyStatistics.size
        }

    val averageBurnedCalories: Int
        get() = if (dailyStatistics.isEmpty()) {
            0
        } else {
            totalBurnedCalories / dailyStatistics.size
        }

    val averageNetCalories: Int
        get() = if (dailyStatistics.isEmpty()) {
            0
        } else {
            dailyStatistics.sumOf { it.netCalories } / dailyStatistics.size
        }

    val maxDisplayCalories: Int
        get() {
            val maxIntake = dailyStatistics.maxOfOrNull { it.intakeCalories } ?: 0
            val maxBurned = dailyStatistics.maxOfOrNull { it.burnedCalories } ?: 0
            val maxTarget = dailyStatistics.maxOfOrNull { it.targetCalories } ?: 0

            return maxOf(
                maxIntake,
                maxBurned,
                maxTarget,
                1
            )
        }
}