package com.example.pftandroidmockproject.data.local.seed

import com.example.pftandroidmockproject.data.local.dao.ActivityTypeDao
import com.example.pftandroidmockproject.data.local.dao.FoodDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseSeeder @Inject constructor(
    private val foodDao: FoodDao,
    private val activityTypeDao: ActivityTypeDao
) {

    suspend fun seedIfNeeded() {
        seedFoodsIfNeeded()
        seedActivityTypesIfNeeded()
    }

    private suspend fun seedFoodsIfNeeded() {
        val currentCount = foodDao.countFoods()

        if (currentCount == 0) {
            foodDao.insertFoods(FoodSeedData.foods)
        }
    }

    private suspend fun seedActivityTypesIfNeeded() {
        val currentCount = activityTypeDao.countActivityTypes()

        if (currentCount == 0) {
            activityTypeDao.insertActivityTypes(ActivityTypeSeedData.activityTypes)
        }
    }
}