package com.example.pftandroidmockproject.data.repository

import com.example.pftandroidmockproject.data.local.dao.FoodDao
import com.example.pftandroidmockproject.data.mapper.toDomain
import com.example.pftandroidmockproject.data.mapper.toEntity
import com.example.pftandroidmockproject.domain.model.meal.Food
import com.example.pftandroidmockproject.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepositoryImpl @Inject constructor(
    private val foodDao: FoodDao
) : FoodRepository {
    override fun getAllFood(): Flow<List<Food>> {
        return foodDao.getAllFoods()
            .map { entities -> entities
                .map { it.toDomain() }
            }
    }

    override fun searchFoods(query: String): Flow<List<Food>> {
        return foodDao.searchFoods(query)
            .map { entities -> entities
                .map { it.toDomain() }}
    }

    override suspend fun getFoodById(foodId: Int): Food? {
        return foodDao.getFoodById(foodId)?.toDomain()
    }

    override suspend fun addFood(food: Food): Food {
        val foodToInsert = food.copy(id = 0)

        val newId = foodDao.insertFood(
            foodToInsert.toEntity()
        ).toInt()

        return foodToInsert.copy(id = newId)
    }

    override suspend fun updateFood(food: Food) {
        foodDao.updateFood(food.toEntity())
    }
}