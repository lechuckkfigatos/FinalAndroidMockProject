package com.example.pftandroidmockproject.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pftandroidmockproject.domain.use_case.dashboard.GetDailyDashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDailyDashboardUseCase: GetDailyDashboardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private var dashBoardJob: Job? = null

    init {
        observeDashboard(LocalDate.now())
    }

    private fun observeDashboard(date: LocalDate) {
        dashBoardJob?.cancel()

        _uiState.update {
            it.copy(
                isLoading = true,
                selectedDate = date,
                errorMessage = null
            )
        }

        dashBoardJob = getDailyDashboardUseCase(date)
            .onEach { summary ->
                if(summary == null){
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            hasProfile = false
                        )
                    }
                    return@onEach
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasProfile = true,
                        targetCalories = summary.targetCalorie,
                        totalIntakeCalories = summary.totalIntakeCalories,
                        totalBurnedCalories = summary.totalBurnedCalories,
                        netCalories = summary.netCalories,
                        remainingCalories = summary.remainingCalories,
                        intakeProgress = summary.intakeProgress,
                        isOverTarget = summary.isOverTarget
                    )
                }
            }
            .catch { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message
                    )
                }
            }
            .launchIn(viewModelScope)
    }


}
