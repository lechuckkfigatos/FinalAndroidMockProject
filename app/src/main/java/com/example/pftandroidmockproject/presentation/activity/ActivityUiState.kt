package com.example.pftandroidmockproject.presentation.activity

import com.example.pftandroidmockproject.domain.model.activity.ActivityEntry
import com.example.pftandroidmockproject.domain.model.activity.ActivityType
import java.time.LocalDate

data class ActivityUiState (
    val isLoading : Boolean =false,

    val selectedDate : LocalDate = LocalDate.now(),

    val activityEntries : List<ActivityEntry> = emptyList(),

    val searchQuery : String = "",
    val activityTypes : List<ActivityType> = emptyList(),
    val selectedActivityType : ActivityType? = null,

    val durationMinutes : String = ""
){
    val totalBurnedCalories : Int
        get() = activityEntries.sumOf { it.caloriesBurned }
}