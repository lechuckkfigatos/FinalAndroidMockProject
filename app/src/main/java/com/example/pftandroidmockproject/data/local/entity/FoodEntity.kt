package com.example.pftandroidmockproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    val servingDescription : String,
    val caloriesPerServing : Int,
    val isCustom : Boolean = false
)