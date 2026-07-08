package com.example.pftandroidmockproject.domain.calculator

import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

class AgeCalculator @Inject constructor() {

    fun calculateAge(
        dateOfBirth : LocalDate,
        currentDate : LocalDate = LocalDate.now()
    ): Int {
        return Period.between(dateOfBirth,currentDate).years
    }
}