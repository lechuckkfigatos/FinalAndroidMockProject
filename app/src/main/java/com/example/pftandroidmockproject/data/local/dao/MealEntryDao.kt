package com.example.pftandroidmockproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pftandroidmockproject.data.local.entity.MealEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealEntryDao {

    @Query("""
        SELECT * FROM meal_entries 
        WHERE date  = :dateEpochDay 
        ORDER BY id DESC
    """)
    fun getMealEntriesByDate(dateEpochDay: Long): Flow<List<MealEntryEntity>>

    @Query("""
        SELECT * FROM meal_entries 
        WHERE date BETWEEN :startEpochDay AND :endEpochDay
        ORDER BY date ASC
    """)
    fun getMealEntriesBetweenDates(
        startEpochDay: Long,
        endEpochDay: Long
    ): Flow<List<MealEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealEntry(entry: MealEntryEntity)

    @Query("DELETE FROM meal_entries WHERE id = :entryId")
    suspend fun deleteMealEntry(entryId: Int)
}