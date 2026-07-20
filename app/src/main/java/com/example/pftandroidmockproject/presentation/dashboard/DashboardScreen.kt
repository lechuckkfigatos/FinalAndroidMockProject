package com.example.pftandroidmockproject.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.presentation.dashboard.components.CalorieProgressCard
import com.example.pftandroidmockproject.presentation.dashboard.components.DashboardAdviceCard
import com.example.pftandroidmockproject.presentation.dashboard.components.DashboardHeader
import com.example.pftandroidmockproject.presentation.dashboard.components.DashboardIntroduction
import com.example.pftandroidmockproject.presentation.dashboard.components.DashboardLoadingCard
import com.example.pftandroidmockproject.presentation.dashboard.components.DashboardMessageCard
import com.example.pftandroidmockproject.presentation.dashboard.components.DashboardSectionTitle
import com.example.pftandroidmockproject.presentation.dashboard.components.DashboardShortcutSection
import com.example.pftandroidmockproject.presentation.dashboard.components.DashboardSummarySection
import com.example.pftandroidmockproject.presentation.profile.components.HealthTrackerHeader
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundBottom
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundTop
import com.example.pftandroidmockproject.presentation.theme.HealthGreen

@Composable
fun DashboardScreen(
    onAddMealClick: () -> Unit,
    onAddActivityClick: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DashboardContent(
        uiState = uiState,
        onAddMealClick = onAddMealClick,
        onAddActivityClick = onAddActivityClick
    )
}

@Composable
private fun DashboardContent(
    uiState: DashboardUiState,
    onAddMealClick: () -> Unit,
    onAddActivityClick: () -> Unit
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
            StaticDashboardHeader()

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
                    DashboardHeader(
                        selectedDate = uiState.selectedDate
                    )
                }

                when {
                    uiState.isLoading -> {
                        item {
                            DashboardLoadingCard()
                        }
                    }

                    uiState.errorMessage != null -> {
                        item {
                            DashboardMessageCard(
                                message = uiState.errorMessage
                            )
                        }
                    }

                    !uiState.hasProfile -> {
                        item {
                            DashboardMessageCard(
                                message = stringResource(
                                    R.string.no_profile_found
                                )
                            )
                        }
                    }

                    else -> {
                        item {
                            CalorieProgressCard(
                                uiState = uiState
                            )
                        }

                        item {
                            DashboardSectionTitle(
                                title = stringResource(
                                    R.string.dashboard_summary_title
                                )
                            )
                        }

                        item {
                            DashboardSummarySection(
                                uiState = uiState
                            )
                        }

                        item {
                            DashboardSectionTitle(
                                title = stringResource(
                                    R.string.dashboard_advice_title
                                )
                            )
                        }

                        item {
                            DashboardAdviceCard(
                                uiState = uiState
                            )
                        }

                        item {
                            DashboardSectionTitle(
                                title = stringResource(
                                    R.string.dashboard_quick_actions
                                )
                            )
                        }

                        item {
                            DashboardShortcutSection(
                                onAddMealClick = onAddMealClick,
                                onAddActivityClick = onAddActivityClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StaticDashboardHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = HealthBackgroundTop.copy(alpha = 0.96f)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp
            ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HealthTrackerHeader(
            title = stringResource(R.string.health_tracker)
        )

        DashboardIntroduction()
    }
}
