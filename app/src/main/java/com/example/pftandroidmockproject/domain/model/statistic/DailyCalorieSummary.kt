package com.example.pftandroidmockproject.domain.model.statistic

data class DailyCalorieSummary (
    val targetCalorie : Int,
    val totalIntakeCalories : Int,
    val totalBurnedCalories : Int,
    val netCalories : Int,
    val remainingCalories: Int,
    val intakeProgress : Float,
    val isOverTarget : Boolean
)
