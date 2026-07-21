package com.example.pftandroidmockproject.presentation.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.setting.AppAccentColor
import com.example.pftandroidmockproject.presentation.theme.HealthAccent

@Composable
fun SettingsPreferenceGroupTitle(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun SettingsAccentColorPicker(
    selectedAccentColor: AppAccentColor,
    onAccentColorSelected: (AppAccentColor) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppAccentColor.entries.forEach { accentColor ->
            SettingsAccentColorOption(
                accentColor = accentColor,
                selected = selectedAccentColor == accentColor,
                onClick = {
                    onAccentColorSelected(accentColor)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SettingsAccentColorOption(
    accentColor: AppAccentColor,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(
                color = if (selected) {
                    accentColor.previewColor().copy(alpha = 0.10f)
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(14.dp)
            )
            .padding(
                horizontal = 6.dp,
                vertical = 8.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(
                    color = accentColor.previewColor(),
                    shape = CircleShape
                )
                .border(
                    width = if (selected) 3.dp else 1.dp,
                    color = if (selected) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.outlineVariant
                    },
                    shape = CircleShape
                )
        )

        Text(
            text = stringResource(accentColor.labelRes()),
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            color = if (selected) {
                accentColor.previewColor()
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    }
}

@Composable
fun SettingsRadioRow(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(
                color = if (selected) {
                    HealthAccent.copy(alpha = 0.08f)
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(14.dp)
            )
            .padding(
                horizontal = 10.dp,
                vertical = 9.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = HealthAccent,
                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            color = if (selected) {
                HealthAccent
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

fun AppAccentColor.previewColor(): Color {
    return when (this) {
        AppAccentColor.GREEN -> Color(0xFF007D3E)
        AppAccentColor.RED -> Color(0xFFC23A3A)
        AppAccentColor.YELLOW -> Color(0xFFC47A00)
        AppAccentColor.BLUE -> Color(0xFF2367C9)
        AppAccentColor.PURPLE -> Color(0xFF7A4ACB)
    }
}

fun AppAccentColor.labelRes(): Int {
    return when (this) {
        AppAccentColor.GREEN -> R.string.theme_color_green
        AppAccentColor.RED -> R.string.theme_color_red
        AppAccentColor.YELLOW -> R.string.theme_color_yellow
        AppAccentColor.BLUE -> R.string.theme_color_blue
        AppAccentColor.PURPLE -> R.string.theme_color_purple
    }
}
