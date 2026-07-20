package com.example.pftandroidmockproject.domain.use_case.profile

import com.example.pftandroidmockproject.domain.model.profile.UserProfile
import com.example.pftandroidmockproject.domain.repository.ProfileRepository
import java.time.LocalDate
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(profile: UserProfile){
        validate(profile)

        profileRepository.saveProfile(profile)
    }
}

private fun validate(profile: UserProfile){
    require(profile.fullName.isNotBlank()) {
        "Full name must not be blank"
    }

    require(profile.dateOfBirth.isBefore(LocalDate.now())) {
        "Invalid date of birth"
    }

    require(profile.weightKg > 0) {
        "Weight must be greater than 0"
    }

    require(profile.heightCm > 0) {
        "Height must be greater than 0"
    }

    require(profile.weightKg in 0.0..300.0) {
        "Invalid Weight"
    }

    require(profile.heightCm in 0.0..250.0) {
        "Invalid Height"
    }
}