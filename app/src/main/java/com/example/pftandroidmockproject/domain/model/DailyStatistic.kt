package com.example.pftandroidmockproject.domain.model

import java.time.LocalDate

data class DailyStatistic(
    val date: LocalDate,
    val targetCalories: Int,
    val intakeCalories: Int,
    val burnedCalories: Int,
    val netCalories: Int,
    val remainingCalories: Int
)
