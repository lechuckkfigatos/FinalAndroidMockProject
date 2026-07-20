package com.example.pftandroidmockproject.domain.model.activity

enum class ActivityLevel(
    val multiplier : Double
) {
    SEDENTARY(1.2),
    LIGHT(1.375,),
    MODERATE(1.55,),
    ACTIVE(1.725,),
    VERY_ACTIVE(1.9,)
}
