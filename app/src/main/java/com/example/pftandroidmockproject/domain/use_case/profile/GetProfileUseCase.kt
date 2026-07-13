package com.example.pftandroidmockproject.domain.use_case.profile

import com.example.pftandroidmockproject.domain.model.UserProfile
import com.example.pftandroidmockproject.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    operator fun invoke(): Flow<UserProfile?>{
        return profileRepository.getProfile()
    }
}