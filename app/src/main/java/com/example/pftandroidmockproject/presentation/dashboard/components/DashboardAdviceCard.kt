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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.presentation.dashboard.DashboardUiState
import com.example.pftandroidmockproject.presentation.theme.HealthAccent

@Composable
fun DashboardAdviceCard(
    uiState: DashboardUiState,
    modifier: Modifier = Modifier
) {
    val isOverTarget = uiState.remainingCalories < 0

    val adviceText = when {
        isOverTarget -> {
            stringResource(
                R.string.dashboard_advice_over_target,
                -uiState.remainingCalories
            )
        }

        uiState.remainingCalories <= 300 -> {
            stringResource(R.string.dashboard_advice_near_target)
        }

        else -> {
            stringResource(
                R.string.dashboard_advice_need_more,
                uiState.remainingCalories
            )
        }
    }

    val accentColor = if (isOverTarget) {
        MaterialTheme.colorScheme.error
    } else {
        HealthAccent
    }

    val backgroundColor = if (isOverTarget) {
        MaterialTheme.colorScheme.errorContainer
    } else {
        HealthAccent.copy(alpha = 0.10f)
    }

    val contentColor = if (isOverTarget) {
        MaterialTheme.colorScheme.onErrorContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(54.dp)
                    .background(
                        color = accentColor,
                        shape = RoundedCornerShape(50)
                    )
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = stringResource(R.string.dashboard_advice),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )

                Text(
                    text = adviceText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor
                )
            }
        }
    }
}