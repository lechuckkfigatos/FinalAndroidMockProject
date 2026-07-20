package com.example.pftandroidmockproject.di

import com.example.pftandroidmockproject.data.repository.ActivityRepositoryImpl
import com.example.pftandroidmockproject.data.repository.FoodRepositoryImpl
import com.example.pftandroidmockproject.data.repository.MealRepositoryImpl
import com.example.pftandroidmockproject.data.repository.ProfileRepositoryImpl
import com.example.pftandroidmockproject.data.repository.SettingsRepositoryImpl
import com.example.pftandroidmockproject.domain.repository.ActivityRepository
import com.example.pftandroidmockproject.domain.repository.FoodRepository
import com.example.pftandroidmockproject.domain.repository.MealRepository
import com.example.pftandroidmockproject.domain.repository.ProfileRepository
import com.example.pftandroidmockproject.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

//@Binds : bind 2 class với nhau
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindFoodRepository(
        impl: FoodRepositoryImpl
    ): FoodRepository

    @Binds
    @Singleton
    abstract fun bindMealRepository(
        impl: MealRepositoryImpl
    ): MealRepository

    @Binds
    @Singleton
    abstract fun bindActivityRepository(
        impl: ActivityRepositoryImpl
    ): ActivityRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository
}