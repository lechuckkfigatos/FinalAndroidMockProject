package com.example.pftandroidmockproject.domain.use_case.food

import com.example.pftandroidmockproject.domain.model.Food
import com.example.pftandroidmockproject.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFoodsUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {

    operator fun invoke(query: String): Flow<List<Food>> {
        val cleanQuery = query.trim()

        return if (cleanQuery.isBlank()) {
            foodRepository.getAllFood()
        } else {
            foodRepository.searchFoods(cleanQuery)
        }
    }
}