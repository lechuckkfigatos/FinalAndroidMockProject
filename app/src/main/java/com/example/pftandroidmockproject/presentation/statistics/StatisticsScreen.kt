package com.example.pftandroidmockproject.presentation.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.statistic.DailyStatistic
import com.example.pftandroidmockproject.presentation.profile.components.HealthTrackerHeader
import com.example.pftandroidmockproject.presentation.statistics.components.*
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundBottom
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundTop
import com.example.pftandroidmockproject.presentation.theme.HealthAccent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs

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
