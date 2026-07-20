package com.example.pftandroidmockproject.domain.repository

import com.example.pftandroidmockproject.domain.model.profile.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository{

    fun getProfile() : Flow<UserProfile?>

    suspend fun getProfileOnce(): UserProfile?

    suspend fun saveProfile(profile: UserProfile)
}