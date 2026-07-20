package com.example.pftandroidmockproject.domain.model.setting

enum class WeightGoal(
    val calorieAdjustment : Int,
) {
    LOSE_WEIGHT(-500,),
    MAINTAIN_WEIGHT(0,),
    GAIN_WEIGHT(500,)

}
