package com.example.pftandroidmockproject.presentation.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.meal.Food
import com.example.pftandroidmockproject.domain.model.meal.MealType
import com.example.pftandroidmockproject.domain.use_case.food.SearchFoodsUseCase
import com.example.pftandroidmockproject.domain.use_case.meal.AddCustomFoodToMealLogUseCase
import com.example.pftandroidmockproject.domain.use_case.meal.AddFoodToMealLogUseCase
import com.example.pftandroidmockproject.domain.use_case.meal.DeleteMealEntryUseCase
import com.example.pftandroidmockproject.domain.use_case.meal.GetMealEntriesByDateUseCase
import com.example.pftandroidmockproject.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MealViewModel @Inject constructor(
    private val getMealEntriesByDateUseCase: GetMealEntriesByDateUseCase,
    private val searchFoodsUseCase: SearchFoodsUseCase,
    private val addFoodToMealLogUseCase: AddFoodToMealLogUseCase,
    private val addCustomFoodToMealLogUseCase: AddCustomFoodToMealLogUseCase,
    private val deleteMealEntryUseCase: DeleteMealEntryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealUiState())
    val uiState: StateFlow<MealUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<MealUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var mealEntriesJob: Job? = null
    private var foodSearchJob: Job? = null

    init {
        observeMealEntries(LocalDate.now())
        observeFoodSearch("")
    }

    fun onPreviousDayClick() {
        val newDate = _uiState.value.selectedDate.minusDays(1)
        onDateChange(newDate)
    }

    fun onNextDayClick() {
        val newDate = _uiState.value.selectedDate.plusDays(1)
        onDateChange(newDate)
    }

    fun onTodayClick() {
        onDateChange(LocalDate.now())
    }

    fun onDateChange(date: LocalDate) {
        observeMealEntries(date)
    }

    fun onMealTypeChange(mealType: MealType) {
        _uiState.update {
            it.copy(
                selectedMealType = mealType
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query,
                selectedFood = null
            )
        }

        observeFoodSearch(query)
    }

    fun onFoodSelected(food: Food) {
        _uiState.update {
            it.copy(
                selectedFood = food
            )
        }
    }

    fun onSelectedFoodServingChange(value: String) {
        _uiState.update {
            it.copy(
                selectedFoodServing = value
            )
        }
    }

    fun onCustomFoodNameChange(value: String) {
        _uiState.update {
            it.copy(
                customFoodName = value
            )
        }
    }

    fun onCustomCaloriesPerServingChange(value: String) {
        _uiState.update {
            it.copy(
                customCaloriesPerServing = value
            )
        }
    }

    fun onCustomServingDescriptionChange(value: String) {
        _uiState.update {
            it.copy(
                customServingDescription = value
            )
        }
    }

    fun onCustomServingChange(value: String) {
        _uiState.update {
            it.copy(
                customServing = value
            )
        }
    }

    fun addSelectedFood() {
        viewModelScope.launch {
            val state = _uiState.value

            val selectedFood = state.selectedFood
            if (selectedFood == null) {
                sendMessage(R.string.error_select_food)
                return@launch
            }

            val serving = state.selectedFoodServing.toDoubleOrNull()
            if (serving == null || serving <= 0) {
                sendMessage(R.string.error_invalid_serving)
                return@launch
            }

            try {
                addFoodToMealLogUseCase(
                    food = selectedFood,
                    serving = serving,
                    date = state.selectedDate,
                    mealType = state.selectedMealType
                )

                _uiState.update {
                    it.copy(
                        selectedFood = null,
                        selectedFoodServing = "1"
                    )
                }

                sendMessage(R.string.food_added)
            } catch (e: Exception) {
                sendMessage(R.string.error_save_profile_failed)
            }
        }
    }

    fun addCustomFood() {
        viewModelScope.launch {
            val state = _uiState.value

            if (state.customFoodName.isBlank()) {
                sendMessage(R.string.error_invalid_food_name)
                return@launch
            }

            val caloriesPerServing = state.customCaloriesPerServing.toIntOrNull()
            if (caloriesPerServing == null || caloriesPerServing <= 0) {
                sendMessage(R.string.error_invalid_calories)
                return@launch
            }

            if (state.customServingDescription.isBlank()) {
                sendMessage(R.string.error_invalid_serving_description)
                return@launch
            }

            val serving = state.customServing.toDoubleOrNull()
            if (serving == null || serving <= 0) {
                sendMessage(R.string.error_invalid_serving)
                return@launch
            }

            try {
                addCustomFoodToMealLogUseCase(
                    name = state.customFoodName,
                    caloriesPerServing = caloriesPerServing,
                    servingDescription = state.customServingDescription,
                    serving = serving,
                    date = state.selectedDate,
                    mealType = state.selectedMealType
                )

                _uiState.update {
                    it.copy(
                        customFoodName = "",
                        customCaloriesPerServing = "",
                        customServingDescription = "",
                        customServing = "1",
                        searchQuery = "",
                        selectedFood = null,
                        selectedFoodServing = "1"
                    )
                }

                observeFoodSearch("")
                sendMessage(R.string.food_added)
            } catch (e: Exception) {
                sendMessage(R.string.error_save_profile_failed)
            }
        }
    }

    fun deleteMealEntry(entryId: Int) {
        viewModelScope.launch {
            try {
                deleteMealEntryUseCase(entryId)

                sendMessage(R.string.food_deleted)
            } catch (e: Exception) {
                sendMessage(R.string.error_save_profile_failed)
            }
        }
    }

    private fun observeMealEntries(date: LocalDate) {
        mealEntriesJob?.cancel()

        _uiState.update {
            it.copy(
                isLoading = true,
                selectedDate = date
            )
        }

        mealEntriesJob = getMealEntriesByDateUseCase(date)
            .catch {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        mealEntries = emptyList()
                    )
                }
            }
            .onEach { entries ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mealEntries = entries
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeFoodSearch(query: String) {
        foodSearchJob?.cancel()

        foodSearchJob = searchFoodsUseCase(query)
            .catch {
                _uiState.update { state ->
                    state.copy(
                        searchResults = emptyList()
                    )
                }
            }
            .onEach { foods ->
                _uiState.update {
                    it.copy(
                        searchResults = foods
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun sendMessage(resId: Int) {
        _uiEvent.send(
            MealUiEvent.ShowMessage(
                UiText.StringResource(resId)
            )
        )
    }
}