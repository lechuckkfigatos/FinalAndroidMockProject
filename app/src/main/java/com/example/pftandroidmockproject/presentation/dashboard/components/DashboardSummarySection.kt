package com.example.pftandroidmockproject.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun DashboardSummarySection(
    uiState: DashboardUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DashboardSummaryCard(
                title = stringResource(R.string.calories_intake_today),
                value = uiState.totalIntakeCalories,
                modifier = Modifier.weight(1f)
            )

            DashboardSummaryCard(
                title = stringResource(R.string.calories_burned_today),
                value = uiState.totalBurnedCalories,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DashboardSummaryCard(
                title = stringResource(R.string.net_calories),
                value = uiState.netCalories,
                modifier = Modifier.weight(1f)
            )

            DashboardSummaryCard(
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
    }
}

@Composable
private fun DashboardSummaryCard(
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

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 13.dp,
                    vertical = 15.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(42.dp)
                    .background(
                        color = valueColor,
                        shape = RoundedCornerShape(50)
                    )
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = formattedValue,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = valueColor
                    )

                    Text(
                        text = stringResource(R.string.kcal),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}