package com.example.pftandroidmockproject.domain.use_case.food

import com.example.pftandroidmockproject.domain.model.Food
import com.example.pftandroidmockproject.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFoodsUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {

    operator fun invoke(): Flow<List<Food>> {
        return foodRepository.getAllFood()
    }
}