package com.example.pftandroidmockproject.presentation.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pftandroidmockproject.domain.use_case.statistic.GetWeeklyStatisticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getWeeklyStatisticsUseCase: GetWeeklyStatisticsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    private var statisticsJob: Job? = null

    init {
        observeWeeklyStatistics(LocalDate.now())
    }

    fun onPreviousWeekClick() {
        val newEndDate = _uiState.value.endDate.minusWeeks(1)
        observeWeeklyStatistics(newEndDate)
    }

    fun onNextWeekClick() {
        val newEndDate = _uiState.value.endDate.plusWeeks(1)
        observeWeeklyStatistics(newEndDate)
    }

    fun onCurrentWeekClick() {
        observeWeeklyStatistics(LocalDate.now())
    }

    private fun observeWeeklyStatistics(endDate: LocalDate) {
        statisticsJob?.cancel()

        _uiState.update {
            it.copy(
                isLoading = true,
                endDate = endDate,
                errorMessage = null
            )
        }

        statisticsJob = getWeeklyStatisticsUseCase(endDate)
            .catch { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message,
                        dailyStatistics = emptyList()
                    )
                }
            }
            .onEach { statistics ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        dailyStatistics = statistics
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}