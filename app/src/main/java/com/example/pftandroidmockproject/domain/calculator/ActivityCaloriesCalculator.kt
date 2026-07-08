package com.example.pftandroidmockproject.domain.calculator

import javax.inject.Inject
import kotlin.math.roundToInt

class ActivityCaloriesCalculator @Inject constructor() {

    fun calculateCaloriesBurned(
        metValue : Double,
        weightKg : Double,
        durationMinutes : Int
    ) : Int{
        val durationHours = durationMinutes/60

        return (metValue * weightKg * durationHours).roundToInt()
    }
}