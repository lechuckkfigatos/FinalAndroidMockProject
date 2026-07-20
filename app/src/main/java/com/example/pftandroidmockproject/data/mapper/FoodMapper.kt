package com.example.pftandroidmockproject.data.mapper

import com.example.pftandroidmockproject.data.local.entity.FoodEntity
import com.example.pftandroidmockproject.domain.model.meal.Food
import com.example.pftandroidmockproject.domain.model.setting.LocalizedText

fun FoodEntity.toDomain() : Food {
    return Food(
        id = id,
        name = LocalizedText(
            vi = nameVi,
            en = nameEn
        ),
        caloriesPerServing = caloriesPerServing ,
        servingDescription = LocalizedText(
            vi = servingDescriptionVi,
            en = servingDescriptionEn
        ),
        isCustom = isCustom
    )
}

fun Food.toEntity() : FoodEntity{
    return FoodEntity(
        id = id,
        nameVi = name.vi,
        nameEn = name.en,

        servingDescriptionVi = servingDescription.vi ,
        servingDescriptionEn = servingDescription.en ,

        caloriesPerServing = caloriesPerServing,
        isCustom = isCustom
    )
}