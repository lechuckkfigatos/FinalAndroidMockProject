package com.example.pftandroidmockproject.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.presentation.dashboard.DashboardUiState
import com.example.pftandroidmockproject.presentation.theme.HealthGreen
import java.text.NumberFormat
import kotlin.math.roundToInt

@Composable
fun CalorieProgressCard(
    uiState: DashboardUiState,
    modifier: Modifier = Modifier
) {
    val displayedProgress = uiState.intakeProgress.coerceIn(
        minimumValue = 0f,
        maximumValue = 1f
    )

    val progressPercent = (
            uiState.intakeProgress.coerceAtLeast(0f) * 100
            ).roundToInt()

    val progressColor = if (uiState.remainingCalories < 0) {
        MaterialTheme.colorScheme.error
    } else {
        HealthGreen
    }

    val formattedIntake = remember(uiState.totalIntakeCalories) {
        NumberFormat
            .getIntegerInstance()
            .format(uiState.totalIntakeCalories)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = stringResource(R.string.calorie_progress),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = stringResource(R.string.calories_intake_today),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Box(
                    modifier = Modifier
                        .background(
                            color = progressColor.copy(alpha = 0.10f),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        )
                ) {
                    Text(
                        text = "$progressPercent%",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = progressColor
                    )
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(164.dp)
            ) {
                CircularProgressIndicator(
                    progress = {
                        1f
                    },
                    modifier = Modifier.size(154.dp),
                    color = progressColor.copy(alpha = 0.10f),
                    trackColor = Color.Transparent,
                    strokeWidth = 13.dp
                )

                CircularProgressIndicator(
                    progress = {
                        displayedProgress
                    },
                    modifier = Modifier.size(154.dp),
                    color = progressColor,
                    trackColor = Color.Transparent,
                    strokeWidth = 13.dp
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = formattedIntake,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = progressColor
                    )

                    Text(
                        text = stringResource(R.string.kcal),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CalorieInformationBox(
                    title = stringResource(R.string.target_calories),
                    value = uiState.targetCalories,
                    modifier = Modifier.weight(1f)
                )

                CalorieInformationBox(
                    title = stringResource(R.string.remaining_calories),
                    value = uiState.remainingCalories,
                    valueColor = if (uiState.remainingCalories < 0) {
                        MaterialTheme.colorScheme.error
                    } else {
                        HealthGreen
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            LinearProgressIndicator(
                progress = {
                    displayedProgress
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = progressColor,
                trackColor = progressColor.copy(alpha = 0.10f)
            )
        }
    }
}

@Composable
private fun CalorieInformationBox(
    title: String,
    value: Int,
    modifier: Modifier = Modifier,
    valueColor: Color = HealthGreen
) {
    val formattedValue = remember(value) {
        NumberFormat
            .getIntegerInstance()
            .format(value)
    }

    Column(
        modifier = modifier
            .background(
                color = valueColor.copy(alpha = 0.08f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 11.dp
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "$formattedValue ${stringResource(R.string.kcal)}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
    }
}