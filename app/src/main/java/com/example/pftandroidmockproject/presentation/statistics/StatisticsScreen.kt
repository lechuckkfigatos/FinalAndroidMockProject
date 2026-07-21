package com.example.pftandroidmockproject.presentation.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.presentation.statistics.components.DailyStatisticsListCard
import com.example.pftandroidmockproject.presentation.statistics.components.LoadingCard
import com.example.pftandroidmockproject.presentation.statistics.components.MessageCard
import com.example.pftandroidmockproject.presentation.statistics.components.StaticStatisticsHeader
import com.example.pftandroidmockproject.presentation.statistics.components.StatisticsWeekCard
import com.example.pftandroidmockproject.presentation.statistics.components.WeeklyLineChartCard
import com.example.pftandroidmockproject.presentation.statistics.components.WeeklyOverviewCard
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundBottom
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundTop

private val BurnedOrange = Color(0xFFE76F51)
private val ChartGridColor = Color(0xFFE7ECEF)

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StatisticsContent(
        uiState = uiState,
        onPreviousWeekClick = viewModel::onPreviousWeekClick,
        onNextWeekClick = viewModel::onNextWeekClick,
        onCurrentWeekClick = viewModel::onCurrentWeekClick
    )
}

@Composable
private fun StatisticsContent(
    uiState: StatisticsUiState,
    onPreviousWeekClick: () -> Unit,
    onNextWeekClick: () -> Unit,
    onCurrentWeekClick: () -> Unit
) {
    Box(
        modifier = Modifier
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
            StaticStatisticsHeader()

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 14.dp,
                    end = 16.dp,
                    bottom = 28.dp
                ),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    StatisticsWeekCard(
                        endDate = uiState.endDate,
                        totalIntakeCalories = uiState.totalIntakeCalories,
                        totalBurnedCalories = uiState.totalBurnedCalories,
                        onPreviousWeekClick = onPreviousWeekClick,
                        onNextWeekClick = onNextWeekClick,
                        onCurrentWeekClick = onCurrentWeekClick
                    )
                }

                when {
                    uiState.isLoading -> {
                        item {
                            LoadingCard()
                        }
                    }

                    uiState.errorMessage != null -> {
                        item {
                            MessageCard(
                                message = uiState.errorMessage
                            )
                        }
                    }

                    uiState.dailyStatistics.isEmpty() -> {
                        item {
                            MessageCard(
                                message = stringResource(R.string.no_statistics_data)
                            )
                        }
                    }

                    else -> {
                        item {
                            WeeklyOverviewCard(
                                uiState = uiState
                            )
                        }

                        item {
                            WeeklyLineChartCard(
                                dailyStatistics = uiState.dailyStatistics,
                                maxDisplayCalories = uiState.maxDisplayCalories
                            )
                        }

                        item {
                            DailyStatisticsListCard(
                                dailyStatistics = uiState.dailyStatistics
                            )
                        }
                    }
                }
            }
        }
    }
}

/* =========================================================
   STATIC HEADER
   ========================================================= */
