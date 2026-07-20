package com.example.pftandroidmockproject.presentation.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pftandroidmockproject.domain.model.Food
import com.example.pftandroidmockproject.domain.model.MealType
import com.example.pftandroidmockproject.presentation.common.asString
import com.example.pftandroidmockproject.presentation.meal.components.*
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundBottom
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundTop
@Composable
fun MealScreen(
    viewModel: MealViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is MealUiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        MealContent(
            uiState = uiState,
            onPreviousDayClick = viewModel::onPreviousDayClick,
            onNextDayClick = viewModel::onNextDayClick,
            onTodayClick = viewModel::onTodayClick,
            onMealTypeChange = viewModel::onMealTypeChange,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            onFoodSelected = viewModel::onFoodSelected,
            onSelectedFoodServingChange = viewModel::onSelectedFoodServingChange,
            onAddSelectedFoodClick = viewModel::addSelectedFood,
            onCustomFoodNameChange = viewModel::onCustomFoodNameChange,
            onCustomCaloriesPerServingChange = viewModel::onCustomCaloriesPerServingChange,
            onCustomServingDescriptionChange = viewModel::onCustomServingDescriptionChange,
            onCustomServingChange = viewModel::onCustomServingChange,
            onAddCustomFoodClick = viewModel::addCustomFood,
            onDeleteMealEntryClick = viewModel::deleteMealEntry,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun MealContent(
    uiState: MealUiState,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit,
    onTodayClick: () -> Unit,
    onMealTypeChange: (MealType) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onFoodSelected: (Food) -> Unit,
    onSelectedFoodServingChange: (String) -> Unit,
    onAddSelectedFoodClick: () -> Unit,
    onCustomFoodNameChange: (String) -> Unit,
    onCustomCaloriesPerServingChange: (String) -> Unit,
    onCustomServingDescriptionChange: (String) -> Unit,
    onCustomServingChange: (String) -> Unit,
    onAddCustomFoodClick: () -> Unit,
    onDeleteMealEntryClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var dialogMode by remember {
        mutableStateOf(AddFoodDialogMode.CLOSED)
    }

    var dialogMealType by remember {
        mutableStateOf(uiState.selectedMealType)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        HealthBackgroundTop,
                        HealthBackgroundBottom
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            StaticMealHeader()

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 10.dp,
                    end = 16.dp,
                    bottom = 28.dp
                ),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    MealDateCard(
                        selectedDate = uiState.selectedDate,
                        totalDayCalories = uiState.totalDayCalories,
                        onPreviousDayClick = onPreviousDayClick,
                        onNextDayClick = onNextDayClick,
                        onTodayClick = onTodayClick
                    )
                }

                item {
                    MealSectionTitle()
                }

                items(
                    items = MealType.entries,
                    key = { mealType ->
                        mealType.name
                    }
                ) { mealType ->
                    val entries = uiState.mealEntries.filter { entry ->
                        entry.mealType == mealType
                    }

                    val totalCalories = entries.sumOf { entry ->
                        entry.totalCalories
                    }

                    MealTypeCard(
                        mealType = mealType,
                        entries = entries,
                        totalCalories = totalCalories,
                        isSelected = uiState.selectedMealType == mealType,
                        onCardClick = {
                            onMealTypeChange(mealType)
                        },
                        onAddFoodClick = {
                            dialogMealType = mealType

                            /*
                             * Quan trọng:
                             * ViewModel dùng selectedMealType để biết
                             * món sắp thêm thuộc bữa nào.
                             */
                            onMealTypeChange(mealType)

                            /*
                             * Reset search để mở dialog existing food
                             * là có danh sách food đầy đủ.
                             */
                            onSearchQueryChange("")

                            dialogMode = AddFoodDialogMode.CHOOSE_METHOD
                        },
                        onDeleteMealEntryClick = { entry ->
                            onDeleteMealEntryClick(entry.id)
                        }
                    )
                }
            }
        }
    }

    if (dialogMode != AddFoodDialogMode.CLOSED) {
        AddFoodDialog(
            mode = dialogMode,
            mealType = dialogMealType,
            uiState = uiState,
            onDismiss = {
                dialogMode = AddFoodDialogMode.CLOSED
            },
            onExistingFoodClick = {
                onSearchQueryChange("")
                dialogMode = AddFoodDialogMode.EXISTING_FOOD
            },
            onCustomFoodClick = {
                dialogMode = AddFoodDialogMode.CUSTOM_FOOD
            },
            onBackClick = {
                dialogMode = AddFoodDialogMode.CHOOSE_METHOD
            },
            onSearchQueryChange = onSearchQueryChange,
            onFoodSelected = onFoodSelected,
            onSelectedFoodServingChange = onSelectedFoodServingChange,
            onAddSelectedFoodClick = {
                onAddSelectedFoodClick()
                dialogMode = AddFoodDialogMode.CLOSED
            },
            onCustomFoodNameChange = onCustomFoodNameChange,
            onCustomCaloriesPerServingChange = onCustomCaloriesPerServingChange,
            onCustomServingDescriptionChange = onCustomServingDescriptionChange,
            onCustomServingChange = onCustomServingChange,
            onAddCustomFoodClick = {
                onAddCustomFoodClick()
                dialogMode = AddFoodDialogMode.CLOSED
            }
        )
    }
}
