package com.example.pftandroidmockproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pftandroidmockproject.data.local.entity.ActivityTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityTypeDao {
    @Query("SELECT * FROM activity_types ORDER BY name ASC")
    fun getAllActivityTypes(): Flow<List<ActivityTypeEntity>>

    @Query("""
        SELECT * FROM activity_types
        WHERE name LIKE "%" || :query || "%"
        ORDER BY name ASC
    """)
    fun searchActivityTypes(query: String): Flow<List<ActivityTypeEntity>>

    @Query("SELECT COUNT(*) FROM activity_types")
    suspend fun countActivityTypes(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityTypes(types: List<ActivityTypeEntity>)
}