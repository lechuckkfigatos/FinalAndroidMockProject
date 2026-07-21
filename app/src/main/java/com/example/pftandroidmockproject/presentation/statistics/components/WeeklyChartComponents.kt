package com.example.pftandroidmockproject.presentation.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pftandroidmockproject.domain.model.statistic.DailyStatistic
import com.example.pftandroidmockproject.presentation.profile.components.HealthTrackerHeader
import com.example.pftandroidmockproject.presentation.statistics.StatisticsUiState
import com.example.pftandroidmockproject.presentation.theme.HealthAccent
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundBottom
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundTop
import com.example.pftandroidmockproject.R
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import kotlin.math.abs

private val BurnedOrange = Color(0xFFE76F51)
private val ChartGridColor = Color(0xFFE7ECEF)

@Composable
fun WeeklyLineChartCard(
    dailyStatistics: List<DailyStatistic>,
    maxDisplayCalories: Int,
    modifier: Modifier = Modifier
) {
    val maxChartCalories = maxDisplayCalories.coerceAtLeast(1)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = uiText(
                        vi = "Biểu đồ 7 ngày",
                        en = "7-day chart"
                    ),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ChartLegendItem(
                        color = HealthAccent,
                        label = stringResource(R.string.intake)
                    )

                    ChartLegendItem(
                        color = BurnedOrange,
                        label = stringResource(R.string.burned)
                    )
                }
            }

            VerticalWeeklyLineChart(
                dailyStatistics = dailyStatistics,
                maxDisplayCalories = maxChartCalories
            )
        }
    }
}

@Composable
fun ChartLegendItem(
    color: Color,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = color,
                    shape = CircleShape
                )
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun VerticalWeeklyLineChart(
    dailyStatistics: List<DailyStatistic>,
    maxDisplayCalories: Int
) {
    val locale = LocalConfiguration.current.locales[0]

    val dayFormatter = remember(locale) {
        DateTimeFormatter.ofPattern(
            "EEE",
            locale
        )
    }

    val statistics = remember(dailyStatistics) {
        dailyStatistics.sortedBy { statistic ->
            statistic.date
        }
    }

    val yLabels = remember(maxDisplayCalories) {
        listOf(
            maxDisplayCalories,
            maxDisplayCalories * 3 / 4,
            maxDisplayCalories / 2,
            maxDisplayCalories / 4,
            0
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(42.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                yLabels.forEach { label ->
                    Text(
                        text = label.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }

            val intakeLineColor = HealthAccent

            Canvas(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                val topPadding = 8.dp.toPx()
                val bottomPadding = 10.dp.toPx()
                val leftPadding = 4.dp.toPx()
                val rightPadding = 8.dp.toPx()
                val chartWidth = size.width - leftPadding - rightPadding
                val chartHeight = size.height - topPadding - bottomPadding
                val chartLeft = leftPadding
                val chartTop = topPadding
                val chartBottom = chartTop + chartHeight
                val maxCalories = maxDisplayCalories.toFloat()

                repeat(5) { index ->
                    val y = chartTop + (chartHeight * index / 4f)

                    drawLine(
                        color = ChartGridColor,
                        start = Offset(chartLeft, y),
                        end = Offset(chartLeft + chartWidth, y),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                drawLine(
                    color = ChartGridColor,
                    start = Offset(chartLeft, chartTop),
                    end = Offset(chartLeft, chartBottom),
                    strokeWidth = 1.dp.toPx()
                )

                fun pointFor(
                    index: Int,
                    value: Int
                ): Offset {
                    val x = if (statistics.size == 1) {
                        chartLeft + chartWidth / 2f
                    } else {
                        chartLeft + chartWidth * index / (statistics.lastIndex).toFloat()
                    }

                    val normalizedValue = (value.toFloat() / maxCalories).coerceIn(0f, 1f)
                    val y = chartBottom - chartHeight * normalizedValue

                    return Offset(x, y)
                }

                fun drawSeries(
                    color: Color,
                    valueSelector: (DailyStatistic) -> Int
                ) {
                    val points = statistics.mapIndexed { index, statistic ->
                        pointFor(
                            index = index,
                            value = valueSelector(statistic)
                        )
                    }

                    points.windowed(2).forEach { pair ->
                        drawLine(
                            color = color,
                            start = pair[0],
                            end = pair[1],
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    points.forEach { point ->
                        drawCircle(
                            color = Color.White,
                            radius = 5.dp.toPx(),
                            center = point
                        )

                        drawCircle(
                            color = color,
                            radius = 4.dp.toPx(),
                            center = point
                        )
                    }
                }

                drawSeries(
                    color = intakeLineColor,
                    valueSelector = { statistic ->
                        statistic.intakeCalories
                    }
                )

                drawSeries(
                    color = BurnedOrange,
                    valueSelector = { statistic ->
                        statistic.burnedCalories
                    }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier.width(42.dp)
            )

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                statistics.forEach { statistic ->
                    Text(
                        text = statistic.date.format(dayFormatter),
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/* =========================================================
   DAILY LIST
   ========================================================= */
