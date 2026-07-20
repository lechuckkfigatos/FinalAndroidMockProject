package com.example.pftandroidmockproject.presentation.activity

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.activity.ActivityEntry
import com.example.pftandroidmockproject.domain.model.activity.ActivityType
import com.example.pftandroidmockproject.domain.model.setting.LocalizedText
import com.example.pftandroidmockproject.presentation.common.asString
import com.example.pftandroidmockproject.presentation.profile.components.HealthTrackerHeader
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundBottom
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundTop
import com.example.pftandroidmockproject.presentation.theme.HealthGreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

@Composable
private fun StaticActivityHeader(
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

        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = stringResource(R.string.activity_log_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = stringResource(R.string.activity_log_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ActivityDateCard(
    selectedDate: LocalDate,
    totalBurnedCalories: Int,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit,
    onTodayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val today = LocalDate.now()
    val isToday = selectedDate == today
    val locale = LocalConfiguration.current.locales[0]

    val dayFormatter = remember(locale) {
        DateTimeFormatter.ofPattern(
            "EEEE",
            locale
        )
    }

    val dateFormatter = remember(locale) {
        DateTimeFormatter.ofPattern(
            "dd MMMM yyyy",
            locale
        )
    }

    val dayText = selectedDate
        .format(dayFormatter)
        .replaceFirstChar { character ->
            if (character.isLowerCase()) {
                character.titlecase(locale)
            } else {
                character.toString()
            }
        }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DateNavigationButton(
                    text = "<",
                    onClick = onPreviousDayClick
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = if (isToday) {
                            stringResource(R.string.today)
                        } else {
                            dayText
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = selectedDate.format(dateFormatter),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (!isToday) {
                        TextButton(
                            onClick = onTodayClick,
                            modifier = Modifier.height(28.dp),
                            contentPadding = PaddingValues(
                                horizontal = 8.dp,
                                vertical = 0.dp
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.back_to_today),
                                color = HealthGreen,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                DateNavigationButton(
                    text = ">",
                    onClick = onNextDayClick
                )
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = HealthGreen.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(
                        horizontal = 14.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = stringResource(R.string.total_burned_calories),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = stringResource(R.string.calories_burned_today),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = totalBurnedCalories.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = HealthGreen
                    )

                    Text(
                        text = stringResource(R.string.kcal),
                        style = MaterialTheme.typography.labelSmall,
                        color = HealthGreen.copy(alpha = 0.75f)
                    )
                }
            }
        }
    }
}

@Composable
private fun DateNavigationButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(34.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = HealthGreen.copy(alpha = 0.10f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = HealthGreen
            )
        }
    }
}

@Composable
private fun AddActivityButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = HealthGreen,
            contentColor = Color.White
        )
    ) {
        Text(
            text = stringResource(R.string.add_activity),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ActivitySectionTitle(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Box(
            modifier = Modifier
                .size(
                    width = 4.dp,
                    height = 21.dp
                )
                .background(
                    color = HealthGreen,
                    shape = RoundedCornerShape(50)
                )
        )

        Text(
            text = stringResource(R.string.activity_entries),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ActivityEntriesCard(
    entries: List<ActivityEntry>,
    onDeleteActivityEntryClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (entries.isEmpty()) {
                EmptyActivityContent()
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    entries.forEachIndexed { index, entry ->
                        if (index > 0) {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                        }

                        ActivityEntryRow(
                            entry = entry,
                            onDeleteClick = {
                                onDeleteActivityEntryClick(entry.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyActivityContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = HealthGreen.copy(alpha = 0.06f),
                shape = RoundedCornerShape(13.dp)
            )
            .padding(14.dp)
    ) {
        Text(
            text = stringResource(R.string.no_activity_entries),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ActivityEntryRow(
    entry: ActivityEntry,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = entry.activityNameSnapshot.displayText(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${entry.durationMinutes} ${stringResource(R.string.minutes)} - ${stringResource(R.string.met_value)} ${entry.metValueSnapshot}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "${entry.caloriesBurned} ${stringResource(R.string.kcal)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = HealthGreen
            )

            TextButton(
                onClick = onDeleteClick,
                contentPadding = PaddingValues(
                    horizontal = 8.dp,
                    vertical = 2.dp
                )
            ) {
                Text(
                    text = stringResource(R.string.delete),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun AddActivityDialog(
    searchQuery: String,
    activityTypes: List<ActivityType>,
    selectedActivityType: ActivityType?,
    durationMinutes: String,
    onDismiss: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onActivityTypeSelected: (ActivityType) -> Unit,
    onDurationMinutesChange: (String) -> Unit,
    onAddActivityClick: () -> Unit
) {
    val validDuration = durationMinutes
        .toIntOrNull()
        ?.let { duration ->
            duration > 0
        } == true

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
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DialogHeader(
                    title = stringResource(R.string.choose_activity),
                    subtitle = stringResource(R.string.add_activity),
                    onCloseClick = onDismiss
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(R.string.search_activity)
                        )
                    },
                    shape = RoundedCornerShape(13.dp),
                    singleLine = true
                )

                Text(
                    text = stringResource(R.string.available_activities),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                if (activityTypes.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(
                                color = HealthGreen.copy(alpha = 0.06f),
                                shape = RoundedCornerShape(14.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No matching activity was found.",
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
                            items = activityTypes,
                            key = { activityType ->
                                activityType.id
                            }
                        ) { activityType ->
                            ActivityTypeRow(
                                activityType = activityType,
                                isSelected = selectedActivityType?.id == activityType.id,
                                onClick = {
                                    onActivityTypeSelected(activityType)
                                }
                            )
                        }
                    }
                }

                if (selectedActivityType != null) {
                    SelectedActivitySummary(
                        activityType = selectedActivityType
                    )
                }

                OutlinedTextField(
                    value = durationMinutes,
                    onValueChange = onDurationMinutesChange,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedActivityType != null,
                    label = {
                        Text(
                            text = stringResource(R.string.duration_minutes)
                        )
                    },
                    shape = RoundedCornerShape(13.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true
                )

                Button(
                    onClick = onAddActivityClick,
                    enabled = selectedActivityType != null && validDuration,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HealthGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.add_activity_log),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogHeader(
    title: String,
    subtitle: String,
    onCloseClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(
                modifier = Modifier.size(1.dp)
            )

            TextButton(
                onClick = onCloseClick
            ) {
                Text(
                    text = "Close",
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

@Composable
private fun ActivityTypeRow(
    activityType: ActivityType,
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
                HealthGreen.copy(alpha = 0.13f)
            } else {
                Color(0xFFF4F7F5)
            }
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) {
                HealthGreen
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
                    text = activityType.name.displayText(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${stringResource(R.string.met_value)}: ${activityType.metValue}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SelectedActivitySummary(
    activityType: ActivityType
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = HealthGreen.copy(alpha = 0.09f)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = HealthGreen.copy(alpha = 0.28f)
        )
    ) {
        Column(
            modifier = Modifier.padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.selected_activity),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = HealthGreen
            )

            Text(
                text = activityType.name.displayText(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${stringResource(R.string.met_value)}: ${activityType.metValue}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun LocalizedText.displayText(): String {
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
