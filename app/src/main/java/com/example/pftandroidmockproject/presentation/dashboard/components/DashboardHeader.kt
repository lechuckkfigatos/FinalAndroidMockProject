package com.example.pftandroidmockproject.presentation.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.presentation.theme.HealthGreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DashboardHeader(
    selectedDate: LocalDate,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit,
    onTodayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val today = LocalDate.now()
    val isToday = selectedDate == today

    val dayFormatter = remember {
        DateTimeFormatter.ofPattern(
            "EEEE",
            Locale.getDefault()
        )
    }

    val dateFormatter = remember {
        DateTimeFormatter.ofPattern(
            "dd MMMM yyyy",
            Locale.getDefault()
        )
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DateNavigationButton(
            text = "‹",
            onClick = onPreviousDayClick
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = if (isToday) {
                    stringResource(R.string.today)
                } else {
                    selectedDate.format(dayFormatter)
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = selectedDate.format(dateFormatter),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (!isToday) {
                TextButton(
                    onClick = onTodayClick
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
            text = "›",
            onClick = onNextDayClick
        )
    }
}

@Composable
private fun DateNavigationButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .size(42.dp)
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        color = HealthGreen.copy(alpha = 0.10f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
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