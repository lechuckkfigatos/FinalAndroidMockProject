package com.example.pftandroidmockproject.presentation.profile.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.activity.ActivityLevel
import com.example.pftandroidmockproject.presentation.mapper.labelRes
import com.example.pftandroidmockproject.presentation.profile.ProfileUiState
import com.example.pftandroidmockproject.presentation.theme.*
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun HealthTrackerHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = HealthAccent,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            LeafIcon(
                modifier = Modifier.size(23.dp)
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = HealthAccent
        )
    }
}

@Composable
private fun LeafIcon(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
    ) {
        val stroke = Stroke(
            width = 2.dp.toPx(),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )

        val stemStart = Offset(
            x = size.width * 0.5f,
            y = size.height * 0.84f
        )

        val stemEnd = Offset(
            x = size.width * 0.5f,
            y = size.height * 0.26f
        )

        drawLine(
            color = Color.White,
            start = stemStart,
            end = stemEnd,
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )

        val leftLeaf = Path().apply {
            moveTo(
                size.width * 0.5f,
                size.height * 0.58f
            )

            cubicTo(
                size.width * 0.20f,
                size.height * 0.57f,
                size.width * 0.18f,
                size.height * 0.29f,
                size.width * 0.25f,
                size.height * 0.20f
            )

            cubicTo(
                size.width * 0.48f,
                size.height * 0.22f,
                size.width * 0.59f,
                size.height * 0.43f,
                size.width * 0.5f,
                size.height * 0.58f
            )

            close()
        }

        val rightLeaf = Path().apply {
            moveTo(
                size.width * 0.5f,
                size.height * 0.68f
            )

            cubicTo(
                size.width * 0.80f,
                size.height * 0.65f,
                size.width * 0.85f,
                size.height * 0.38f,
                size.width * 0.77f,
                size.height * 0.28f
            )

            cubicTo(
                size.width * 0.55f,
                size.height * 0.31f,
                size.width * 0.43f,
                size.height * 0.52f,
                size.width * 0.5f,
                size.height * 0.68f
            )

            close()
        }

        drawPath(
            path = leftLeaf,
            color = Color.White,
            style = stroke
        )

        drawPath(
            path = rightLeaf,
            color = Color.White,
            style = stroke
        )
    }
}


@Composable
fun ProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ProfileFieldLabel(
            text = label
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            cursorBrush = SolidColor(HealthAccent),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(
                            color = HealthFieldBackground,
                            shape = RoundedCornerShape(11.dp)
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isBlank()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyMedium,
                                color = HealthSecondaryText.copy(
                                    alpha = 0.55f
                                )
                            )
                        }

                        innerTextField()
                    }
                }
            }
        )
    }
}




@Composable
fun ProfileReadOnlyField(
    value: String,
    placeholder: String,
    trailingContent: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = HealthFieldBackground,
                shape = RoundedCornerShape(11.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value.ifBlank { placeholder },
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            color = if (value.isBlank()) {
                HealthSecondaryText.copy(alpha = 0.55f)
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )

        Spacer(
            modifier = Modifier.width(6.dp)
        )

        trailingContent()
    }
}

@Composable
fun ProfileFieldLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

fun LocalDate.toUtcEpochMillis(): Long {
    return atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()
}

fun Long.toLocalDateUtc(): LocalDate {
    return Instant
        .ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
}