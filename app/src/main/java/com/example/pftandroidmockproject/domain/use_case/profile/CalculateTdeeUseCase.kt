package com.example.pftandroidmockproject.domain.use_case.profile

import com.example.pftandroidmockproject.domain.calculator.TdeeCalculator
import com.example.pftandroidmockproject.domain.model.UserProfile
import javax.inject.Inject

class CalculateTdeeUseCase @Inject constructor(
    val tdeeCalculator: TdeeCalculator
){
    operator fun invoke(profile: UserProfile) : Int {
        return tdeeCalculator.calculateTdee(profile)
    }
}