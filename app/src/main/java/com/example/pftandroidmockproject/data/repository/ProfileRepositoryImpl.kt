package com.example.pftandroidmockproject.data.repository

import com.example.pftandroidmockproject.data.local.dao.UserProfileDao
import com.example.pftandroidmockproject.data.mapper.toDomain
import com.example.pftandroidmockproject.data.mapper.toEntity
import com.example.pftandroidmockproject.domain.model.profile.UserProfile
import com.example.pftandroidmockproject.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
) : ProfileRepository {

    override fun getProfile(): Flow<UserProfile?> {
        return userProfileDao.getProfile()
            .map { entity ->
                entity?.toDomain()
            }
    }

    override suspend fun getProfileOnce(): UserProfile? {
        return userProfileDao.getProfileOnce()?.toDomain()
    }

    override suspend fun saveProfile(profile: UserProfile) {
        userProfileDao.saveProfile(profile.toEntity())
    }
}