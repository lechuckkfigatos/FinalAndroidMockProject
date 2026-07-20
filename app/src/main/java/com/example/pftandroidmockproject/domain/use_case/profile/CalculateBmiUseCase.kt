package com.example.pftandroidmockproject.domain.use_case.profile

import com.example.pftandroidmockproject.domain.calculator.BmiCalculator
import com.example.pftandroidmockproject.domain.model.profile.BmiCategory
import com.example.pftandroidmockproject.domain.model.profile.UserProfile
import javax.inject.Inject

class CalculateBmiUseCase @Inject constructor(
    val bmiCalculator: BmiCalculator
) {
    operator fun invoke(profile : UserProfile) : BmiResult{
        val bmi  = bmiCalculator.calculateBmi(
            weightKg = profile.weightKg,
            heightCm = profile.heightCm
        )

        val category = bmiCalculator.getCategory(bmi)
        return BmiResult(
            value = bmi,
            category = category
        )
    }
}

data class BmiResult(
    val value : Double,
    val category : BmiCategory
)
