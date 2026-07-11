package com.example.pftandroidmockproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_entries")
data class MealEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    val date : Long,
    val mealType : String,

    // null nếu user tự nhập món
    val foodId : Int?,

    val foodNameSnapshotVi : String,
    val foodNameSnapshotEn : String,

    val servingDescriptionSnapshotVi : String,
    val servingDescriptionSnapshotEn : String,

    val caloriesPerServingSnapshot : Int,

    val serving : Double,
    val totalCalories : Int,

    val isCustom: Boolean
)
