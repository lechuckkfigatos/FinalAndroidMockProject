package com.example.pftandroidmockproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pftandroidmockproject.data.local.entity.ActivityEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityEntryDao {
    @Query("""
        SELECT * FROM activity_entries 
        WHERE date = :date 
        ORDER BY id ASC
    """)
    fun getActivityEntriesByDate(date : Long): Flow<List<ActivityEntryEntity>>

    @Query("""
        SELECT * FROM activity_entries
        WHERE date BETWEEN :startDate AND :endDate
        ORDER BY id ASC 
    """)
    fun getActivityEntriesBetweenDates(
        startDate : Long,
        endDate: Long
    ): Flow<List<ActivityEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityEntry(entry: ActivityEntryEntity)

    @Query("DELETE FROM activity_entries WHERE id = :entryId")
    suspend fun deleteActivityEntry(entryId : Int)
}