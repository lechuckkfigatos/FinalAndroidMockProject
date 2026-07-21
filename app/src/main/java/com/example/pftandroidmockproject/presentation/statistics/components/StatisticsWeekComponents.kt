package com.example.pftandroidmockproject.presentation.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.presentation.theme.HealthAccent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val BurnedOrange = Color(0xFFE76F51)

@Composable
fun StatisticsWeekCard(
    endDate: LocalDate,
    totalIntakeCalories: Int,
    totalBurnedCalories: Int,
    onPreviousWeekClick: () -> Unit,
    onNextWeekClick: () -> Unit,
    onCurrentWeekClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val startDate = endDate.minusDays(6)
    val locale = LocalConfiguration.current.locales[0]

    val formatter = remember(locale) {
        DateTimeFormatter.ofPattern(
            "dd/MM",
            locale
        )
    }

    val today = LocalDate.now()
    val isCurrentWeek = endDate == today

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
                StatisticsNavigationButton(
                    text = "<",
                    onClick = onPreviousWeekClick
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = stringResource(R.string.weekly_overview),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${startDate.format(formatter)} - ${endDate.format(formatter)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (!isCurrentWeek) {
                        TextButton(
                            onClick = onCurrentWeekClick,
                            modifier = Modifier.height(28.dp),
                            contentPadding = PaddingValues(
                                horizontal = 8.dp,
                                vertical = 0.dp
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.today),
                                color = HealthAccent,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                StatisticsNavigationButton(
                    text = ">",
                    onClick = onNextWeekClick
                )
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = HealthAccent.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(
                        horizontal = 14.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeeklyTotalItem(
                    title = stringResource(R.string.total_intake),
                    calories = totalIntakeCalories,
                    color = HealthAccent,
                    modifier = Modifier.weight(1f)
                )

                WeeklyTotalItem(
                    title = stringResource(R.string.total_burned),
                    calories = totalBurnedCalories,
                    color = BurnedOrange,
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                )
            }
        }
    }
}

@Composable
fun StatisticsNavigationButton(
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
            containerColor = HealthAccent.copy(alpha = 0.10f)
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
                color = HealthAccent
            )
        }
    }
}

@Composable
fun WeeklyTotalItem(
    title: String,
    calories: Int,
    color: Color,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = calories.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )

            Text(
                text = stringResource(R.string.kcal),
                style = MaterialTheme.typography.labelSmall,
                color = color.copy(alpha = 0.75f)
            )
        }
    }
}

/* =========================================================
   OVERVIEW CARDS
   ========================================================= */
