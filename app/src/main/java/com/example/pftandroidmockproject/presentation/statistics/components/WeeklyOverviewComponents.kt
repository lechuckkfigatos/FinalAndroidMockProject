package com.example.pftandroidmockproject.presentation.statistics.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.presentation.statistics.StatisticsUiState
import com.example.pftandroidmockproject.presentation.theme.HealthAccent

@Composable
fun WeeklyOverviewCard(
    uiState: StatisticsUiState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.weekly_overview),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatisticSmallCard(
                    title = stringResource(R.string.average_intake),
                    value = uiState.averageIntakeCalories,
                    modifier = Modifier.weight(1f)
                )

                StatisticSmallCard(
                    title = stringResource(R.string.average_burned),
                    value = uiState.averageBurnedCalories,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatisticSmallCard(
                    title = stringResource(R.string.average_net),
                    value = uiState.averageNetCalories,
                    modifier = Modifier.weight(1f)
                )

                StatisticSmallCard(
                    title = stringResource(R.string.total_burned),
                    value = uiState.totalBurnedCalories,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun StatisticSmallCard(
    title: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = HealthAccent.copy(alpha = 0.08f)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = HealthAccent.copy(alpha = 0.18f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "$value ${stringResource(R.string.kcal)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = HealthAccent
            )
        }
    }
}

/* =========================================================
   LINE CHART
   ========================================================= */
