package com.example.pftandroidmockproject.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pftandroidmockproject.data.local.dao.UserProfileDao
import com.example.pftandroidmockproject.data.local.entity.ActivityEntryEntity
import com.example.pftandroidmockproject.data.local.entity.ActivityTypeEntity
import com.example.pftandroidmockproject.data.local.entity.FoodEntity
import com.example.pftandroidmockproject.data.local.entity.MealEntryEntity
import com.example.pftandroidmockproject.data.local.entity.UserProfileEntity

@Database(
    entities = [
        UserProfileEntity::class,
        FoodEntity::class,
        MealEntryEntity::class,
        ActivityTypeEntity::class,
        ActivityEntryEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase(){

    abstract fun userProfileDao() : UserProfileDao


}

