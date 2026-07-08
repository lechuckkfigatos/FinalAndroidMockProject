package com.example.pftandroidmockproject.domain.calculator

import com.example.pftandroidmockproject.domain.model.BmiCategory
import javax.inject.Inject
import kotlin.math.roundToInt

class BmiCalculator @Inject constructor() {

    fun calculateBmi(
        weightKg : Double,
        heightCm : Double
    ): Double{
        val heightM = heightCm/100
        val bmi = weightKg/ (heightM * heightM)

        return bmi
    }

    fun getCategory(bmi : Double) : BmiCategory{
        return when{
            bmi < 18.5 -> BmiCategory.UNDERWEIGHT
            bmi < 25 -> BmiCategory.NORMAL
            bmi < 30 -> BmiCategory.OVERWEIGHT
            else -> BmiCategory.OBESE
        }
    }
}