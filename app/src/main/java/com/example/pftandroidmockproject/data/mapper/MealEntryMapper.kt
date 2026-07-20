package com.example.pftandroidmockproject.data.mapper

import com.example.pftandroidmockproject.data.local.entity.MealEntryEntity
import com.example.pftandroidmockproject.domain.model.setting.LocalizedText
import com.example.pftandroidmockproject.domain.model.meal.MealEntry
import com.example.pftandroidmockproject.domain.model.meal.MealType
import java.time.LocalDate

fun MealEntryEntity.toDomain() : MealEntry {
    return MealEntry(
        id = id,
        date = LocalDate.ofEpochDay(date),
        mealType = MealType.valueOf(mealType),
        foodId = foodId,

        foodNameSnapshot = LocalizedText(
            vi = foodNameSnapshotVi,
            en = foodNameSnapshotEn
        ),

        servingDescriptionSnapshot = LocalizedText(
            vi = servingDescriptionSnapshotVi,
            en = servingDescriptionSnapshotEn
        ),

        caloriesPerServingSnapshot = caloriesPerServingSnapshot,
        serving = serving,
        totalCalories = totalCalories,

    )
}

fun MealEntry.toEntity() : MealEntryEntity{
    return MealEntryEntity(
        id = id,
        date = date.toEpochDay(),
        mealType = mealType.name,
        foodId = foodId,

        foodNameSnapshotVi = foodNameSnapshot.vi,
        foodNameSnapshotEn = foodNameSnapshot.en,

        servingDescriptionSnapshotVi = servingDescriptionSnapshot.vi,
        servingDescriptionSnapshotEn = servingDescriptionSnapshot.en,

        caloriesPerServingSnapshot = caloriesPerServingSnapshot,
        serving = serving,
        totalCalories = totalCalories
    )
}