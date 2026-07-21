package com.example.pftandroidmockproject.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.activity.ActivityLevel
import com.example.pftandroidmockproject.presentation.theme.HealthAccent
import com.example.pftandroidmockproject.presentation.theme.HealthSecondaryText
import kotlin.collections.indexOf
import kotlin.math.roundToInt

@Composable
fun ActivityLevelSlider(
    selectedValue: ActivityLevel?,
    options: List<ActivityLevel>,
    optionText: @Composable (ActivityLevel) -> String,
    onValueChange: (ActivityLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    if (options.isEmpty()) {
        return
    }

    val selectedIndex = options
        .indexOf(selectedValue)
        .takeIf { index -> index >= 0 }
        ?: 0

    val displayedOption = options[selectedIndex]

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ProfileFieldLabel(
                text = stringResource(R.string.activity_level)
            )

            Text(
                text = buildString {
                    append(optionText(displayedOption))
                    append(" (")
                    append(selectedIndex + 1)
                    append(")")
                },
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = HealthAccent
            )
        }

        Slider(
            value = selectedIndex.toFloat(),
            onValueChange = { sliderValue ->
                val newIndex = sliderValue
                    .roundToInt()
                    .coerceIn(options.indices)

                onValueChange(options[newIndex])
            },
            valueRange = 0f..options.lastIndex.toFloat(),
            steps = (options.size - 2).coerceAtLeast(0),
            colors = SliderDefaults.colors(
                thumbColor = HealthAccent,
                activeTrackColor = HealthAccent,
                inactiveTrackColor = Color(0xFFE1E5E4)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.activity_low),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = HealthSecondaryText
            )

            Text(
                text = stringResource(R.string.activity_high),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = HealthSecondaryText
            )
        }
    }
}
