package com.example.pftandroidmockproject.presentation.meal.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.meal.Food
import com.example.pftandroidmockproject.domain.model.setting.LocalizedText
import com.example.pftandroidmockproject.domain.model.meal.MealEntry
import com.example.pftandroidmockproject.domain.model.meal.MealType
import com.example.pftandroidmockproject.presentation.mapper.labelRes
import com.example.pftandroidmockproject.presentation.profile.components.HealthTrackerHeader
import com.example.pftandroidmockproject.presentation.meal.MealUiState
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundTop
import com.example.pftandroidmockproject.presentation.theme.HealthAccent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddFoodDialog(
    mode: AddFoodDialogMode,
    mealType: MealType,
    uiState: MealUiState,
    onDismiss: () -> Unit,
    onExistingFoodClick: () -> Unit,
    onCustomFoodClick: () -> Unit,
    onBackClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onFoodSelected: (Food) -> Unit,
    onSelectedFoodServingChange: (String) -> Unit,
    onAddSelectedFoodClick: () -> Unit,
    onCustomFoodNameChange: (String) -> Unit,
    onCustomCaloriesPerServingChange: (String) -> Unit,
    onCustomServingDescriptionChange: (String) -> Unit,
    onCustomServingChange: (String) -> Unit,
    onAddCustomFoodClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.86f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            when (mode) {
                AddFoodDialogMode.CHOOSE_METHOD -> {
                    AddFoodMethodContent(
                        mealType = mealType,
                        onExistingFoodClick = onExistingFoodClick,
                        onCustomFoodClick = onCustomFoodClick,
                        onCloseClick = onDismiss
                    )
                }

                AddFoodDialogMode.EXISTING_FOOD -> {
                    ExistingFoodContent(
                        mealType = mealType,
                        searchQuery = uiState.searchQuery,
                        searchResults = uiState.searchResults,
                        selectedFood = uiState.selectedFood,
                        selectedFoodServing = uiState.selectedFoodServing,
                        onBackClick = onBackClick,
                        onCloseClick = onDismiss,
                        onSearchQueryChange = onSearchQueryChange,
                        onFoodSelected = onFoodSelected,
                        onSelectedFoodServingChange = onSelectedFoodServingChange,
                        onAddClick = onAddSelectedFoodClick
                    )
                }

                AddFoodDialogMode.CUSTOM_FOOD -> {
                    CustomFoodContent(
                        mealType = mealType,
                        name = uiState.customFoodName,
                        caloriesPerServing = uiState.customCaloriesPerServing,
                        servingDescription = uiState.customServingDescription,
                        serving = uiState.customServing,
                        onBackClick = onBackClick,
                        onCloseClick = onDismiss,
                        onNameChange = onCustomFoodNameChange,
                        onCaloriesChange = onCustomCaloriesPerServingChange,
                        onServingDescriptionChange = onCustomServingDescriptionChange,
                        onServingChange = onCustomServingChange,
                        onAddClick = onAddCustomFoodClick
                    )
                }

                AddFoodDialogMode.CLOSED -> Unit
            }
        }
    }
}

@Composable
fun DialogHeader(
    title: String,
    subtitle: String,
    onCloseClick: () -> Unit,
    onBackClick: (() -> Unit)? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (onBackClick != null) {
                TextButton(
                    onClick = onBackClick
                ) {
                    Text(
                        text = "< ${
                            uiText(
                                vi = "Quay lại",
                                en = "Back"
                            )
                        }",
                        color = HealthAccent,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                Spacer(
                    modifier = Modifier.size(1.dp)
                )
            }

            TextButton(
                onClick = onCloseClick
            ) {
                Text(
                    text = uiText(
                        vi = "Đóng",
                        en = "Close"
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

//LOCALIZATION HELPERS

@Composable
fun AddFoodMethodContent(
    mealType: MealType,
    onExistingFoodClick: () -> Unit,
    onCustomFoodClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    val mealTypeText = stringResource(mealType.labelRes())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        DialogHeader(
            title = uiText(
                vi = "Thêm đồ ăn",
                en = "Add food"
            ),
            subtitle = uiText(
                vi = "Đồ ăn sẽ được thêm vào $mealTypeText.",
                en = "Food will be added to $mealTypeText."
            ),
            onCloseClick = onCloseClick
        )

        Text(
            text = uiText(
                vi = "Bạn muốn thêm món bằng cách nào?",
                en = "How would you like to add food?"
            ),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        AddFoodMethodItem(
            title = uiText(
                vi = "Chọn món có sẵn",
                en = "Choose existing food"
            ),
            description = uiText(
                vi = "Lướt danh sách đồ ăn và chọn một món.",
                en = "Browse the food list and select an item."
            ),
            onClick = onExistingFoodClick
        )

        AddFoodMethodItem(
            title = uiText(
                vi = "Tạo món tùy chỉnh",
                en = "Create custom food"
            ),
            description = uiText(
                vi = "Tự nhập tên món, calo và khẩu phần.",
                en = "Enter the name, calories and serving information."
            ),
            onClick = onCustomFoodClick
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        OutlinedButton(
            onClick = onCloseClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = HealthAccent
            )
        ) {
            Text(
                text = stringResource(R.string.cancel),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AddFoodMethodItem(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = HealthAccent.copy(alpha = 0.08f)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = HealthAccent.copy(alpha = 0.25f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(17.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = HealthAccent
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/* =========================================================
   EXISTING FOOD DIALOG
   ========================================================= */

@Composable
fun ExistingFoodContent(
    mealType: MealType,
    searchQuery: String,
    searchResults: List<Food>,
    selectedFood: Food?,
    selectedFoodServing: String,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onFoodSelected: (Food) -> Unit,
    onSelectedFoodServingChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    val mealTypeText = stringResource(mealType.labelRes())

    val validServing = selectedFoodServing
        .toDoubleOrNull()
        ?.let { serving ->
            serving > 0
        } == true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DialogHeader(
            title = uiText(
                vi = "Chọn món có sẵn",
                en = "Choose existing food"
            ),
            subtitle = uiText(
                vi = "Thêm vào $mealTypeText",
                en = "Add to $mealTypeText"
            ),
            onBackClick = onBackClick,
            onCloseClick = onCloseClick
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.search_food)
                )
            },
            placeholder = {
                Text(
                    text = uiText(
                        vi = "Tìm theo tên món ăn",
                        en = "Search by food name"
                    )
                )
            },
            shape = RoundedCornerShape(13.dp),
            singleLine = true
        )

        Text(
            text = uiText(
                vi = "Danh sách đồ ăn",
                en = "Available foods"
            ),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )

        if (searchResults.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = HealthAccent.copy(alpha = 0.06f),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiText(
                        vi = "Không tìm thấy món ăn phù hợp.",
                        en = "No matching food was found."
                    ),
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = searchResults,
                    key = { food ->
                        "${food.id}-${food.name.vi}-${food.name.en}"
                    }
                ) { food ->
                    FoodSearchResultRow(
                        food = food,
                        isSelected = selectedFood?.id == food.id,
                        onClick = {
                            onFoodSelected(food)
                        }
                    )
                }
            }
        }

        if (selectedFood != null) {
            SelectedFoodSummary(
                food = selectedFood
            )
        }

        OutlinedTextField(
            value = selectedFoodServing,
            onValueChange = onSelectedFoodServingChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedFood != null,
            label = {
                Text(
                    text = stringResource(R.string.serving_amount)
                )
            },
            shape = RoundedCornerShape(13.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true
        )

        Button(
            onClick = onAddClick,
            enabled = selectedFood != null && validServing,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = HealthAccent,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.add_selected_food),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FoodSearchResultRow(
    food: Food,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                HealthAccent.copy(alpha = 0.13f)
            } else {
                Color(0xFFF4F7F5)
            }
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) {
                HealthAccent
            } else {
                MaterialTheme.colorScheme.outlineVariant
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = food.name.displayText(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = food.servingDescription.displayText(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = food.caloriesPerServing.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = HealthAccent
                )

                Text(
                    text = stringResource(R.string.kcal),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun SelectedFoodSummary(
    food: Food
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = HealthAccent.copy(alpha = 0.09f)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = HealthAccent.copy(alpha = 0.28f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = uiText(
                        vi = "Món đã chọn",
                        en = "Selected food"
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = HealthAccent
                )

                Text(
                    text = food.name.displayText(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = food.servingDescription.displayText(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "${food.caloriesPerServing} ${stringResource(R.string.kcal)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = HealthAccent
            )
        }
    }
}

// CUSTOM FOOD DIALOG

@Composable
fun CustomFoodContent(
    mealType: MealType,
    name: String,
    caloriesPerServing: String,
    servingDescription: String,
    serving: String,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onCaloriesChange: (String) -> Unit,
    onServingDescriptionChange: (String) -> Unit,
    onServingChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    val mealTypeText = stringResource(mealType.labelRes())

    val validCalories = caloriesPerServing
        .toIntOrNull()
        ?.let { calories ->
            calories > 0
        } == true

    val validServing = serving
        .toDoubleOrNull()
        ?.let { servingValue ->
            servingValue > 0
        } == true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DialogHeader(
            title = uiText(
                vi = "Tạo món tùy chỉnh",
                en = "Create custom food"
            ),
            subtitle = uiText(
                vi = "Thêm vào $mealTypeText",
                en = "Add to $mealTypeText"
            ),
            onBackClick = onBackClick,
            onCloseClick = onCloseClick
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(R.string.food_name)
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.food_name_hint)
                        )
                    },
                    shape = RoundedCornerShape(13.dp),
                    singleLine = true
                )
            }

            item {
                OutlinedTextField(
                    value = caloriesPerServing,
                    onValueChange = onCaloriesChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(R.string.calories_per_serving)
                        )
                    },
                    shape = RoundedCornerShape(13.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true
                )
            }

            item {
                OutlinedTextField(
                    value = servingDescription,
                    onValueChange = onServingDescriptionChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(R.string.serving_description)
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.serving_description_hint)
                        )
                    },
                    shape = RoundedCornerShape(13.dp),
                    singleLine = true
                )
            }

            item {
                OutlinedTextField(
                    value = serving,
                    onValueChange = onServingChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(R.string.serving_amount)
                        )
                    },
                    shape = RoundedCornerShape(13.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    singleLine = true
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = HealthAccent.copy(alpha = 0.08f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = uiText(
                                vi = "Bữa ăn",
                                en = "Meal"
                            ),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = mealTypeText,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = HealthAccent
                        )
                    }
                }
            }
        }

        Button(
            onClick = onAddClick,
            enabled = name.isNotBlank() &&
                    validCalories &&
                    servingDescription.isNotBlank() &&
                    validServing,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = HealthAccent,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.add_custom_food),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/* =========================================================
   COMMON DIALOG HEADER
   ========================================================= */

@Composable
fun uiText(
    vi: String,
    en: String
): String {
    val language = LocalConfiguration
        .current
        .locales[0]
        .language

    return if (language == "vi") {
        vi
    } else {
        en
    }
}

