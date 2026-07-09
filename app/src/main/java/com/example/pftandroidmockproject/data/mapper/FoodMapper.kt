package com.example.pftandroidmockproject.data.mapper

import com.example.pftandroidmockproject.data.local.entity.FoodEntity
import com.example.pftandroidmockproject.domain.model.Food

fun FoodEntity.toDomain() : Food {
    return Food(
        id = id,
        name = name,
        caloriesPerServing = caloriesPerServing ,
        servingDescription = servingDescription,
        isCustom = isCustom
    )
}

fun Food.toEntity() : FoodEntity{
    return FoodEntity(
        id = id,
        name = name,
        servingDescription = servingDescription ,
        caloriesPerServing = caloriesPerServing,
        isCustom = isCustom
    )
}