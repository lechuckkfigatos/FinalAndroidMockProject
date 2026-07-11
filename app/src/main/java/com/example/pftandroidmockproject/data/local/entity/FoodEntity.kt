package com.example.pftandroidmockproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    val nameVi : String,
    val nameEn : String,

    val servingDescriptionVi : String,
    val servingDescriptionEn : String,


    val caloriesPerServing : Int,
    val isCustom : Boolean = false
)