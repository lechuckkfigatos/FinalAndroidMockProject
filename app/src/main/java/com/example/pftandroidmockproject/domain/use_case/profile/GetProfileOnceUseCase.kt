package com.example.pftandroidmockproject.domain.use_case.profile

import com.example.pftandroidmockproject.domain.model.UserProfile
import com.example.pftandroidmockproject.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileOnceUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke() : UserProfile? {
        return profileRepository.getProfileOnce()
    }
}