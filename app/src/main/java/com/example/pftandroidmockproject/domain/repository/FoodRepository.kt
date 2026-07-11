package com.example.pftandroidmockproject.domain.repository

import com.example.pftandroidmockproject.domain.model.Food
import kotlinx.coroutines.flow.Flow

interface FoodRepository {

    fun getAllFood(): Flow<List<Food>>

    fun searchFoods(query : String):Flow<List<Food>>

    suspend fun addFood(food: Food)
}