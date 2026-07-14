package com.example.pftandroidmockproject.presentation.profile.components

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
import androidx.compose.material3.Surface
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
import com.example.pftandroidmockproject.presentation.profile.ProfileUiState
import com.example.pftandroidmockproject.presentation.theme.HealthDivider
import com.example.pftandroidmockproject.presentation.theme.HealthGreen
import com.example.pftandroidmockproject.presentation.theme.HealthGreenSoft
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HealthPreviewCard(
    uiState: ProfileUiState,
    modifier: Modifier = Modifier
) {
    val tdeeText = remember(uiState.tdeeCalories) {
        uiState.tdeeCalories?.let { calories ->
            NumberFormat
                .getIntegerInstance()
                .format(calories)
        } ?: "--"
    }

    val bmiText = remember(uiState.bmiValue) {
        uiState.bmiValue?.let { bmi ->
            String.format(
                Locale.getDefault(),
                "%.1f",
                bmi
            )
        } ?: "--"
    }

    val bmiCategoryText = uiState.bmiCategory?.let { category ->
        stringResource(category.labelRes())
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = HealthGreenSoft
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 14.dp,
                    vertical = 13.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = stringResource(R.string.daily_calorie_target),
                    style = MaterialTheme.typography.labelMedium,
                    color = HealthGreen
                )

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = tdeeText,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = HealthGreen
                    )

                    Text(
                        text = stringResource(R.string.kcal).uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = HealthGreen.copy(alpha = 0.65f),
                        modifier = Modifier.padding(
                            bottom = 3.dp
                        )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(48.dp)
                    .background(HealthDivider)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 13.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = stringResource(R.string.bmi),
                    style = MaterialTheme.typography.labelMedium,
                    color = HealthGreen
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = bmiText,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = HealthGreen
                    )

                    if (bmiCategoryText != null) {
                        Surface(
                            color = HealthGreen.copy(alpha = 0.10f),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = bmiCategoryText,
                                modifier = Modifier.padding(
                                    horizontal = 6.dp,
                                    vertical = 3.dp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelSmall,
                                color = HealthGreen
                            )
                        }
                    }
                }
            }
        }
    }
}