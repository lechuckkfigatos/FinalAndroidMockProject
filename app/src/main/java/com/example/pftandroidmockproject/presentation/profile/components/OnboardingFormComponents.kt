package com.example.pftandroidmockproject.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.activity.ActivityLevel
import com.example.pftandroidmockproject.domain.model.profile.Gender
import com.example.pftandroidmockproject.domain.model.setting.WeightGoal
import com.example.pftandroidmockproject.presentation.mapper.labelRes
import com.example.pftandroidmockproject.presentation.profile.ProfileUiState
import com.example.pftandroidmockproject.presentation.theme.HealthAccent
import java.time.LocalDate

@Composable
fun OnboardingIntro(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(R.string.onboarding_title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = stringResource(R.string.onboarding_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun OnboardingProfileFormCard(
    uiState: ProfileUiState,
    onFullNameChange: (String) -> Unit,
    onDateOfBirthChange: (LocalDate) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onActivityLevelChange: (ActivityLevel) -> Unit,
    onGoalChange: (WeightGoal) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ProfileTextField(
                value = uiState.fullName,
                onValueChange = onFullNameChange,
                label = stringResource(R.string.full_name),
                placeholder = stringResource(R.string.full_name_hint),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileDatePickerField(
                    selectedDate = uiState.dateOfBirth,
                    onDateSelected = onDateOfBirthChange,
                    modifier = Modifier.weight(1f)
                )

                ProfileDropdownField(
                    label = stringResource(R.string.gender),
                    selectedValue = uiState.gender,
                    options = Gender.entries.toList(),
                    optionText = { gender ->
                        stringResource(gender.labelRes())
                    },
                    onSelect = onGenderChange,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileTextField(
                    value = uiState.weightKg,
                    onValueChange = onWeightChange,
                    label = "${stringResource(R.string.weight)} (${stringResource(R.string.kg)})",
                    placeholder = "65",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    modifier = Modifier.weight(1f)
                )

                ProfileTextField(
                    value = uiState.heightCm,
                    onValueChange = onHeightChange,
                    label = "${stringResource(R.string.height)} (${stringResource(R.string.cm)})",
                    placeholder = "170",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            ActivityLevelSlider(
                selectedValue = uiState.activityLevel,
                options = ActivityLevel.entries.toList(),
                optionText = { activityLevel ->
                    stringResource(activityLevel.labelRes())
                },
                onValueChange = onActivityLevelChange
            )

            ProfileDropdownField(
                label = stringResource(R.string.goal),
                selectedValue = uiState.goal,
                options = WeightGoal.entries.toList(),
                optionText = { goal ->
                    stringResource(goal.labelRes())
                },
                onSelect = onGoalChange,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            SaveProfileButton(
                isSaving = uiState.isSaving,
                onClick = onSaveClick
            )
        }
    }
}

@Composable
fun SaveProfileButton(
    isSaving: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = !isSaving,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = HealthAccent,
            contentColor = Color.White,
            disabledContainerColor = HealthAccent.copy(
                alpha = 0.55f
            ),
            disabledContentColor = Color.White
        ),
        contentPadding = PaddingValues(
            horizontal = 18.dp,
            vertical = 12.dp
        )
    ) {
        if (isSaving) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )
        }

        Text(
            text = stringResource(R.string.save_profile),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        if (!isSaving) {
            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null
            )
        }
    }
}
