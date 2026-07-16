package com.example.pftandroidmockproject.presentation.dashboard

import java.time.LocalDate

data class DashboardUiState (
    val isLoading : Boolean = false,
    val hasProfile : Boolean = true,

    val selectedDate : LocalDate = LocalDate.now(),

    val targetCalories : Int = 0,
    val totalIntakeCalories :Int = 0,
    val totalBurnedCalories: Int = 0,
    val netCalories : Int = 0,
    val remainingCalories : Int = 0,

    //for circle
    val intakeProgress : Float = 0f,

    val isOverTarget : Boolean = false,

    val errorMessage : String ? = null
)