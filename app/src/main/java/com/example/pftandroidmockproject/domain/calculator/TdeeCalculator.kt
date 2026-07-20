package com.example.pftandroidmockproject.domain.calculator

import com.example.pftandroidmockproject.domain.model.Gender
import com.example.pftandroidmockproject.domain.model.UserProfile
import javax.inject.Inject
import kotlin.math.roundToInt

class TdeeCalculator @Inject constructor(
    private val ageCalculator: AgeCalculator
) {
    fun calculateBmr(profile : UserProfile) : Double {
        val age = ageCalculator.calculateAge(profile.dateOfBirth)

        return when(profile.gender){
            Gender.MALE -> {
                10 * profile.weightKg + 6.25 * profile.heightCm - 5 * age + 5
            }
            Gender.FEMALE -> {
                10 * profile.weightKg + 6.25 * profile.heightCm - 5 * age - 161
            }
        }
    }

    fun calculateTdee(profile: UserProfile) : Int {
        val bmr = calculateBmr(profile)
        val tdeeBeforeGoal = bmr * profile.activityLevel.multiplier
        val finalTdee = tdeeBeforeGoal + profile.goal.calorieAdjustment

        return finalTdee.roundToInt()
    }
}