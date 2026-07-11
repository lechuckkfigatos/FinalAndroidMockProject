package com.example.pftandroidmockproject.di

import android.content.Context
import androidx.room.Room
import com.example.pftandroidmockproject.data.local.dao.ActivityEntryDao
import com.example.pftandroidmockproject.data.local.dao.ActivityTypeDao
import com.example.pftandroidmockproject.data.local.dao.FoodDao
import com.example.pftandroidmockproject.data.local.dao.MealEntryDao
import com.example.pftandroidmockproject.data.local.dao.UserProfileDao
import com.example.pftandroidmockproject.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module         //Bảo hilt đây là tập hợp các công thức tạo dêpendency
@InstallIn(SingletonComponent::class)
// bảo hilt những thứ đc tạo ra trong file này có lifecycle cùng app (open -> close)
object DatabaseModule {
    @Provides   //chứa recipe tạo ra vật phẩm ở đuôi (AppDatabase ?)
    @Singleton  // chỉ có 1 thực thể trong lúc app chạy
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "health_tracker_database"
        )
            .build()
    }

    @Provides //recipe tạo UserProfileDao
    fun provideUserProfileDao(
        database: AppDatabase
    ): UserProfileDao {
        return database.userProfileDao()
    }

    @Provides
    fun provideFoodDao(database: AppDatabase): FoodDao {
        return database.foodDao()
    }

    @Provides
    fun provideMealEntryDao(database: AppDatabase): MealEntryDao {
        return database.mealEntryDao()
    }

    @Provides
    fun provideActivityTypeDao(database: AppDatabase): ActivityTypeDao {
        return database.activityTypeDao()
    }

    @Provides
    fun provideActivityEntryDao(database: AppDatabase): ActivityEntryDao {
        return database.activityEntryDao()
    }
}
