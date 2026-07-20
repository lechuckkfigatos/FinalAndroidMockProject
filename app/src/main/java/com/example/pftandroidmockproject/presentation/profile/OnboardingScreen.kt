package com.example.pftandroidmockproject.presentation.profile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.activity.ActivityLevel
import com.example.pftandroidmockproject.domain.model.profile.Gender
import com.example.pftandroidmockproject.domain.model.setting.WeightGoal
import com.example.pftandroidmockproject.presentation.common.asString
import com.example.pftandroidmockproject.presentation.mapper.labelRes
import com.example.pftandroidmockproject.presentation.profile.components.ActivityLevelSlider
import com.example.pftandroidmockproject.presentation.profile.components.HealthPreviewCard
import com.example.pftandroidmockproject.presentation.profile.components.HealthTrackerHeader
import com.example.pftandroidmockproject.presentation.profile.components.ProfileDatePickerField
import com.example.pftandroidmockproject.presentation.profile.components.ProfileDropdownField
import com.example.pftandroidmockproject.presentation.profile.components.ProfileTextField
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundBottom
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundTop
import com.example.pftandroidmockproject.presentation.theme.HealthGreen
import java.time.LocalDate

@Composable
fun OnboardingScreen(
    onProfileSaved: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ProfileUiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }

                ProfileUiEvent.SaveSuccess -> {
                    onProfileSaved()
                }
            }
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        OnboardingContent(
            uiState = uiState,
            onFullNameChange = viewModel::onFullNameChange,
            onDateOfBirthChange = viewModel::onDateOfBirthChange,
            onGenderChange = viewModel::onGenderChange,
            onWeightChange = viewModel::onWeightChange,
            onHeightChange = viewModel::onHeightChange,
            onActivityLevelChange = viewModel::onActivityLevelChange,
            onGoalChange = viewModel::onGoalChange,
            onSaveClick = viewModel::saveProfile,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun OnboardingContent(
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
    Box(
        modifier = modifier
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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HealthTrackerHeader(
                title = stringResource(R.string.health_tracker)
            )

            Column(
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

            /*
             * Card này lấy dữ liệu đã được tính trong ViewModel:
             *
             * uiState.tdeeCalories
             * uiState.bmiValue
             * uiState.bmiCategory
             */
            HealthPreviewCard(
                uiState = uiState
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
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
                            label = buildString {
                                append(stringResource(R.string.weight))
                                append(" (")
                                append(stringResource(R.string.kg))
                                append(")")
                            },
                            placeholder = "65",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        ProfileTextField(
                            value = uiState.heightCm,
                            onValueChange = onHeightChange,
                            label = buildString {
                                append(stringResource(R.string.height))
                                append(" (")
                                append(stringResource(R.string.cm))
                                append(")")
                            },
                            placeholder = "170",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    /*
                     * Slider vẫn dùng ActivityLevel enum của domain.
                     * Không lưu Float vào ViewModel.
                     */
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

                    Button(
                        onClick = onSaveClick,
                        enabled = !uiState.isSaving,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HealthGreen,
                            contentColor = Color.White,
                            disabledContainerColor = HealthGreen.copy(
                                alpha = 0.55f
                            ),
                            disabledContentColor = Color.White
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 18.dp,
                            vertical = 12.dp
                        )
                    ) {
                        if (uiState.isSaving) {
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

                        if (!uiState.isSaving) {
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
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }
    }
}