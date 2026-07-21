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

@Composable
private fun StaticStatisticsHeader(
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
                text = stringResource(R.string.statistics_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = stringResource(R.string.statistics_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/* =========================================================
   WEEK SELECTOR
   ========================================================= */

@Composable
private fun StatisticsWeekCard(
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
private fun StatisticsNavigationButton(
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
private fun WeeklyTotalItem(
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

@Composable
private fun WeeklyOverviewCard(
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
private fun StatisticSmallCard(
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

@Composable
private fun WeeklyLineChartCard(
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
private fun ChartLegendItem(
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
private fun VerticalWeeklyLineChart(
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

@Composable
private fun DailyStatisticsListCard(
    dailyStatistics: List<DailyStatistic>,
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
                text = uiText(
                    vi = "Chi tiết từng ngày",
                    en = "Daily details"
                ),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            dailyStatistics.forEachIndexed { index, statistic ->
                if (index > 0) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }

                DailyStatisticRow(
                    statistic = statistic
                )
            }
        }
    }
}

@Composable
private fun DailyStatisticRow(
    statistic: DailyStatistic
) {
    val locale = LocalConfiguration.current.locales[0]

    val dateFormatter = remember(locale) {
        DateTimeFormatter.ofPattern(
            "EEE, dd/MM",
            locale
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = statistic.date.format(dateFormatter),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${stringResource(R.string.intake)}: ${statistic.intakeCalories} · ${stringResource(R.string.burned)}: ${statistic.burnedCalories}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        val netColor = if (statistic.netCalories > statistic.targetCalories) {
            MaterialTheme.colorScheme.error
        } else {
            HealthAccent
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = "${statistic.netCalories} ${stringResource(R.string.kcal)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = netColor
            )

            Text(
                text = "${stringResource(R.string.net)} ${formatSigned(statistic.remainingCalories)}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/* =========================================================
   STATE CARDS
   ========================================================= */

@Composable
private fun LoadingCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun MessageCard(
    message: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(18.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

/* =========================================================
   HELPERS
   ========================================================= */

@Composable
private fun uiText(
    vi: String,
    en: String
): String {
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

private fun formatSigned(value: Int): String {
    return if (value >= 0) {
        "+$value"
    } else {
        "-${abs(value)}"
    }
}
