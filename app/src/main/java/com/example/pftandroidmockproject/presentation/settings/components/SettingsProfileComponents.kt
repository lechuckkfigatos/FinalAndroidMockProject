package com.example.pftandroidmockproject.presentation.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.presentation.mapper.labelRes
import com.example.pftandroidmockproject.presentation.settings.SettingsUiState
import com.example.pftandroidmockproject.presentation.theme.HealthAccent
import java.util.Locale

@Composable
fun SettingsBmiPreview(
    uiState: SettingsUiState
) {
    val bmiText = remember(uiState.bmiValue) {
        uiState.bmiValue?.let { bmi ->
            String.format(
                Locale.getDefault(),
                "%.1f",
                bmi
            )
        } ?: "--"
    }

    val categoryText = uiState.bmiCategory?.let { category ->
        stringResource(category.labelRes())
    } ?: stringResource(R.string.bmi_waiting_for_body_info)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = HealthAccent.copy(alpha = 0.08f),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = HealthAccent.copy(alpha = 0.18f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                horizontal = 14.dp,
                vertical = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.current_bmi),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = categoryText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = bmiText,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = HealthAccent
            )

            Text(
                text = stringResource(R.string.bmi),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
