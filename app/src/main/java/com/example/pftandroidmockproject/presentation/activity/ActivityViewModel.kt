package com.example.pftandroidmockproject.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.ActivityType
import com.example.pftandroidmockproject.domain.use_case.activity.DeleteActivityEntryUseCase
import com.example.pftandroidmockproject.domain.use_case.activity.GetActivityEntriesByDateUseCase
import com.example.pftandroidmockproject.domain.use_case.activity.SearchActivityTypesUseCase
import com.example.pftandroidmockproject.domain.usecase.activity.AddActivityToLogUseCase
import com.example.pftandroidmockproject.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
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
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getActivityEntriesByDateUseCase: GetActivityEntriesByDateUseCase,
    private val searchActivityTypesUseCase: SearchActivityTypesUseCase,
    private val addActivityToLogUseCase: AddActivityToLogUseCase,
    private val deleteActivityEntryUseCase: DeleteActivityEntryUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow(ActivityUiState())
    val uiState : StateFlow<ActivityUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel <ActivityUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var activityEntriesJob : Job? = null
    private var activitySearchJob : Job? = null

    init {
        observeActivityEntries(LocalDate.now())
        observeActivitySearch("")
    }
    
    fun onPreviousDayClick() {
        val newDate = _uiState.value.selectedDate.plusDays(-1)
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
        observeActivityEntries(date)
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query,
                selectedActivityType = null
            )
        }

        observeActivitySearch(query)
    }

    fun onActivityTypeSelected(activityType: ActivityType) {
        _uiState.update {
            it.copy(
                selectedActivityType = activityType
            )
        }
    }

    fun onDurationMinutesChange(value: String) {
        _uiState.update {
            it.copy(
                durationMinutes = value
            )
        }
    }

    fun addActivity() {
        viewModelScope.launch {
            val state = _uiState.value

            val selectedActivityType = state.selectedActivityType
            if (selectedActivityType == null) {
                sendMessage(R.string.error_select_activity)
                return@launch
            }

            val durationMinutes = state.durationMinutes.toIntOrNull()
            if (durationMinutes == null || durationMinutes <= 0) {
                sendMessage(R.string.error_invalid_duration)
                return@launch
            }

            try {
                addActivityToLogUseCase(
                    activityType = selectedActivityType,
                    durationMinutes = durationMinutes,
                    date = state.selectedDate
                )

                _uiState.update {
                    it.copy(
                        selectedActivityType = null,
                        durationMinutes = "",
                        searchQuery = ""
                    )
                }

                observeActivitySearch("")
                sendMessage(R.string.activity_added)
            } catch (e: Exception) {
                sendMessage(R.string.error_save_profile_failed)
            }
        }
    }

    fun deleteActivityEntry(entryId: Int) {
        viewModelScope.launch {
            try {
                deleteActivityEntryUseCase(entryId)
                sendMessage(R.string.activity_deleted)
            } catch (e: Exception) {
                sendMessage(R.string.error_save_profile_failed)
            }
        }
    }

    private fun observeActivityEntries(date: LocalDate) {
        activityEntriesJob?.cancel()

        _uiState.update {
            it.copy(
                isLoading = true,
                selectedDate = date
            )
        }

        activityEntriesJob = getActivityEntriesByDateUseCase(date)
            .catch {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        activityEntries = emptyList()
                    )
                }
            }
            .onEach { entries ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        activityEntries = entries
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeActivitySearch(query: String) {
        activitySearchJob?.cancel()

        activitySearchJob = searchActivityTypesUseCase(query)
            .catch {
                _uiState.update { state ->
                    state.copy(
                        activityTypes = emptyList()
                    )
                }
            }
            .onEach { activityTypes ->
                _uiState.update {
                    it.copy(
                        activityTypes = activityTypes
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun sendMessage(resId: Int) {
        _uiEvent.send(
            ActivityUiEvent.ShowMessage(
                UiText.StringResource(resId)
            )
        )
    }
}