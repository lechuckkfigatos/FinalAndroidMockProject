package com.example.pftandroidmockproject.presentation.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.pftandroidmockproject.domain.model.activity.ActivityType
import com.example.pftandroidmockproject.presentation.activity.components.ActivityDateCard
import com.example.pftandroidmockproject.presentation.activity.components.ActivityEntriesCard
import com.example.pftandroidmockproject.presentation.activity.components.ActivitySectionTitle
import com.example.pftandroidmockproject.presentation.activity.components.AddActivityButton
import com.example.pftandroidmockproject.presentation.activity.components.AddActivityDialog
import com.example.pftandroidmockproject.presentation.activity.components.StaticActivityHeader
import com.example.pftandroidmockproject.presentation.common.asString
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundBottom
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundTop

@Composable
fun ActivityLogScreen(
    viewModel: ActivityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ActivityUiEvent.ShowMessage -> {
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
        ActivityLogContent(
            uiState = uiState,
            onPreviousDayClick = viewModel::onPreviousDayClick,
            onNextDayClick = viewModel::onNextDayClick,
            onTodayClick = viewModel::onTodayClick,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            onActivityTypeSelected = viewModel::onActivityTypeSelected,
            onDurationMinutesChange = viewModel::onDurationMinutesChange,
            onAddActivityClick = viewModel::addActivity,
            onDeleteActivityEntryClick = viewModel::deleteActivityEntry,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun ActivityLogContent(
    uiState: ActivityUiState,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit,
    onTodayClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onActivityTypeSelected: (ActivityType) -> Unit,
    onDurationMinutesChange: (String) -> Unit,
    onAddActivityClick: () -> Unit,
    onDeleteActivityEntryClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddActivityDialog by remember {
        mutableStateOf(false)
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
            StaticActivityHeader()

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
                    ActivityDateCard(
                        selectedDate = uiState.selectedDate,
                        totalBurnedCalories = uiState.totalBurnedCalories,
                        onPreviousDayClick = onPreviousDayClick,
                        onNextDayClick = onNextDayClick,
                        onTodayClick = onTodayClick
                    )
                }

                item {
                    AddActivityButton(
                        onClick = {
                            onSearchQueryChange("")
                            showAddActivityDialog = true
                        }
                    )
                }

                item {
                    ActivitySectionTitle()
                }

                item {
                    ActivityEntriesCard(
                        entries = uiState.activityEntries,
                        onDeleteActivityEntryClick = onDeleteActivityEntryClick
                    )
                }
            }
        }
    }

    if (showAddActivityDialog) {
        AddActivityDialog(
            searchQuery = uiState.searchQuery,
            activityTypes = uiState.activityTypes,
            selectedActivityType = uiState.selectedActivityType,
            durationMinutes = uiState.durationMinutes,
            onDismiss = {
                showAddActivityDialog = false
            },
            onSearchQueryChange = onSearchQueryChange,
            onActivityTypeSelected = onActivityTypeSelected,
            onDurationMinutesChange = onDurationMinutesChange,
            onAddActivityClick = {
                onAddActivityClick()
                showAddActivityDialog = false
            }
        )
    }
}
