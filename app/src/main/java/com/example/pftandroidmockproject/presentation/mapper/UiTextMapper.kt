package com.example.pftandroidmockproject.presentation.mapper

import androidx.annotation.StringRes
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.ActivityLevel
import com.example.pftandroidmockproject.domain.model.Gender
import com.example.pftandroidmockproject.domain.model.MealType
import com.example.pftandroidmockproject.domain.model.WeightGoal

@StringRes
fun Gender.labelRes(): Int {
    return when (this) {
        Gender.MALE -> R.string.gender_male
        Gender.FEMALE -> R.string.gender_female
    }
}

@StringRes
fun MealType.labelRes(): Int {
    return when (this) {
        MealType.BREAKFAST -> R.string.meal_breakfast
        MealType.LUNCH -> R.string.meal_lunch
        MealType.DINNER -> R.string.meal_dinner
        MealType.SNACK -> R.string.meal_snack
    }
}

@StringRes
fun WeightGoal.labelRes(): Int {
    return when (this) {
        WeightGoal.LOSE_WEIGHT -> R.string.goal_lose_weight
        WeightGoal.MAINTAIN_WEIGHT -> R.string.goal_maintain_weight
        WeightGoal.GAIN_WEIGHT -> R.string.goal_gain_weight
    }
}


fun ActivityLevel.labelRes(): Int {
    return when (this) {
        ActivityLevel.SEDENTARY -> R.string.activity_level_sedentary
        ActivityLevel.LIGHT -> R.string.activity_level_light
        ActivityLevel.MODERATE -> R.string.activity_level_moderate
        ActivityLevel.ACTIVE -> R.string.activity_level_active
        ActivityLevel.VERY_ACTIVE -> R.string.activity_level_very_active
    }
}