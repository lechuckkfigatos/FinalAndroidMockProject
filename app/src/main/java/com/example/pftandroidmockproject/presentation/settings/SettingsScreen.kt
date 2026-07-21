package com.example.pftandroidmockproject.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.activity.ActivityLevel
import com.example.pftandroidmockproject.domain.model.profile.Gender
import com.example.pftandroidmockproject.domain.model.setting.AppAccentColor
import com.example.pftandroidmockproject.domain.model.setting.AppFontSize
import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode
import com.example.pftandroidmockproject.domain.model.setting.WeightGoal
import com.example.pftandroidmockproject.presentation.common.asString
import com.example.pftandroidmockproject.presentation.mapper.labelRes
import com.example.pftandroidmockproject.presentation.profile.components.ActivityLevelSlider
import com.example.pftandroidmockproject.presentation.profile.components.HealthTrackerHeader
import com.example.pftandroidmockproject.presentation.profile.components.ProfileDatePickerField
import com.example.pftandroidmockproject.presentation.profile.components.ProfileDropdownField
import com.example.pftandroidmockproject.presentation.profile.components.ProfileTextField
import com.example.pftandroidmockproject.presentation.settings.components.*
import com.example.pftandroidmockproject.presentation.theme.HealthAccent
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundBottom
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundTop
import java.time.LocalDate
import java.util.Locale

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SettingsUiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
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
        SettingsContent(
            uiState = uiState,
            onFullNameChange = viewModel::onFullNameChange,
            onDateOfBirthChange = viewModel::onDateOfBirthChange,
            onGenderChange = viewModel::onGenderChange,
            onWeightChange = viewModel::onWeightChange,
            onHeightChange = viewModel::onHeightChange,
            onActivityLevelChange = viewModel::onActivityLevelChange,
            onGoalChange = viewModel::onGoalChange,
            onSaveProfileClick = viewModel::saveProfile,
            onLanguageSelected = viewModel::onLanguageSelected,
            onThemeModeSelected = viewModel::onThemeModeSelected,
            onFontSizeSelected = viewModel::onFontSizeSelected,
            onAccentColorSelected = viewModel::onAccentColorSelected,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun SettingsContent(
    uiState: SettingsUiState,
    onFullNameChange: (String) -> Unit,
    onDateOfBirthChange: (LocalDate) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onActivityLevelChange: (ActivityLevel) -> Unit,
    onGoalChange: (WeightGoal) -> Unit,
    onSaveProfileClick: () -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
    onThemeModeSelected: (AppThemeMode) -> Unit,
    onFontSizeSelected: (AppFontSize) -> Unit,
    onAccentColorSelected: (AppAccentColor) -> Unit,
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
            modifier = Modifier.fillMaxSize()
        ) {
            StaticSettingsHeader()

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
                if (uiState.isLoading) {
                    item {
                        SettingsLoadingCard()
                    }
                } else {
                    item {
                        SettingsSectionTitle(
                            title = stringResource(R.string.edit_profile)
                        )
                    }

                    item {
                        SettingsOptionCard {
                            SettingsBmiPreview(
                                uiState = uiState
                            )

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
                        }
                    }

                    item {
                        SettingsSectionTitle(
                            title = stringResource(R.string.lifestyle_and_goal)
                        )
                    }

                    item {
                        SettingsOptionCard {
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

                            Button(
                                onClick = onSaveProfileClick,
                                enabled = !uiState.isProfileSaving,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(13.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = HealthAccent,
                                    contentColor = Color.White
                                )
                            ) {
                                if (uiState.isProfileSaving) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .height(18.dp)
                                            .width(18.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        text = stringResource(R.string.save_profile),
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    item {
                        SettingsSectionTitle(
                            title = stringResource(R.string.preferences)
                        )
                    }

                    item {
                        SettingsOptionCard {
                            SettingsPreferenceGroupTitle(
                                title = stringResource(R.string.theme)
                            )

                            SettingsRadioRow(
                                title = stringResource(R.string.theme_system),
                                selected = uiState.selectedThemeMode == AppThemeMode.SYSTEM,
                                onClick = {
                                    onThemeModeSelected(AppThemeMode.SYSTEM)
                                }
                            )

                            SettingsRadioRow(
                                title = stringResource(R.string.theme_light),
                                selected = uiState.selectedThemeMode == AppThemeMode.LIGHT,
                                onClick = {
                                    onThemeModeSelected(AppThemeMode.LIGHT)
                                }
                            )

                            SettingsRadioRow(
                                title = stringResource(R.string.theme_dark),
                                selected = uiState.selectedThemeMode == AppThemeMode.DARK,
                                onClick = {
                                    onThemeModeSelected(AppThemeMode.DARK)
                                }
                            )

                            SettingsInnerDivider()

                            SettingsPreferenceGroupTitle(
                                title = stringResource(R.string.theme_color)
                            )

                            SettingsAccentColorPicker(
                                selectedAccentColor = uiState.selectedAccentColor,
                                onAccentColorSelected = onAccentColorSelected
                            )

                            SettingsInnerDivider()

                            SettingsPreferenceGroupTitle(
                                title = stringResource(R.string.language)
                            )

                            SettingsRadioRow(
                                title = stringResource(R.string.vietnamese),
                                selected = uiState.selectedLanguage == AppLanguage.VI,
                                onClick = {
                                    onLanguageSelected(AppLanguage.VI)
                                }
                            )

                            SettingsRadioRow(
                                title = stringResource(R.string.english),
                                selected = uiState.selectedLanguage == AppLanguage.EN,
                                onClick = {
                                    onLanguageSelected(AppLanguage.EN)
                                }
                            )

                            SettingsInnerDivider()

                            SettingsPreferenceGroupTitle(
                                title = stringResource(R.string.font_size)
                            )

                            SettingsRadioRow(
                                title = stringResource(R.string.font_size_small),
                                selected = uiState.selectedFontSize == AppFontSize.SMALL,
                                onClick = {
                                    onFontSizeSelected(AppFontSize.SMALL)
                                }
                            )

                            SettingsRadioRow(
                                title = stringResource(R.string.font_size_medium),
                                selected = uiState.selectedFontSize == AppFontSize.MEDIUM,
                                onClick = {
                                    onFontSizeSelected(AppFontSize.MEDIUM)
                                }
                            )

                            SettingsRadioRow(
                                title = stringResource(R.string.font_size_large),
                                selected = uiState.selectedFontSize == AppFontSize.LARGE,
                                onClick = {
                                    onFontSizeSelected(AppFontSize.LARGE)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
