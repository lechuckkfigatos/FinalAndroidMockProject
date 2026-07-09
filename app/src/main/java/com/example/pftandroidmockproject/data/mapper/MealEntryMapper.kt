package com.example.pftandroidmockproject.data.mapper

import com.example.pftandroidmockproject.data.local.entity.MealEntryEntity
import com.example.pftandroidmockproject.domain.model.MealEntry
import com.example.pftandroidmockproject.domain.model.MealType
import java.time.LocalDate

fun MealEntryEntity.toDomain() : MealEntry {
    return MealEntry(
        id = id,
        date = LocalDate.ofEpochDay(date),
        mealType = MealType.valueOf(mealType),
        foodId = foodId,
        foodNameSnapshot = foodNameSnapshot,
        servingDescriptionSnapshot = servingDescriptionSnapshot,
        caloriesPerServingSnapshot = caloriesPerServingSnapshot,
        serving = serving,
        totalCalories = totalCalories
    )
}

fun MealEntry.toEntity() : MealEntryEntity{
    return MealEntryEntity(
        id = id,
        date = date.toEpochDay(),
        mealType = mealType.name,
        foodId = foodId,
        foodNameSnapshot = foodNameSnapshot,
        servingDescriptionSnapshot = servingDescriptionSnapshot,
        caloriesPerServingSnapshot = caloriesPerServingSnapshot,
        serving = serving,
        totalCalories = totalCalories
    )
}