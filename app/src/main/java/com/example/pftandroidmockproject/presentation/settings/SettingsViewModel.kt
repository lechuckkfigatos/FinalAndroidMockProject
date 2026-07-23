package com.example.pftandroidmockproject.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.calculator.BmiCalculator
import com.example.pftandroidmockproject.domain.model.activity.ActivityLevel
import com.example.pftandroidmockproject.domain.model.profile.Gender
import com.example.pftandroidmockproject.domain.model.profile.ProfileLimits
import com.example.pftandroidmockproject.domain.model.profile.UserProfile
import com.example.pftandroidmockproject.domain.model.setting.AppAccentColor
import com.example.pftandroidmockproject.domain.model.setting.AppFontSize
import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode
import com.example.pftandroidmockproject.domain.model.setting.WeightGoal
import com.example.pftandroidmockproject.domain.use_case.profile.GetProfileOnceUseCase
import com.example.pftandroidmockproject.domain.use_case.profile.SaveProfileUseCase
import com.example.pftandroidmockproject.domain.use_case.setting.GetSettingsUseCase
import com.example.pftandroidmockproject.domain.use_case.setting.UpdateAccentColorUseCase
import com.example.pftandroidmockproject.domain.use_case.setting.UpdateFontSizeUseCase
import com.example.pftandroidmockproject.domain.use_case.setting.UpdateLanguageUseCase
import com.example.pftandroidmockproject.domain.use_case.setting.UpdateThemeModeUseCase
import com.example.pftandroidmockproject.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import java.time.LocalDate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateThemeModeUseCase: UpdateThemeModeUseCase,
    private val updateFontSizeUseCase: UpdateFontSizeUseCase,
    private val updateAccentColorUseCase: UpdateAccentColorUseCase,
    private val getProfileOnceUseCase: GetProfileOnceUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val bmiCalculator: BmiCalculator
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<SettingsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        observeSettings()
        loadProfile()
    }

    fun onLanguageSelected(language: AppLanguage) {
        viewModelScope.launch {
            updateLanguageUseCase(language)
        }
    }

    fun onThemeModeSelected(themeMode: AppThemeMode) {
        viewModelScope.launch {
            updateThemeModeUseCase(themeMode)
        }
    }

    fun onFontSizeSelected(fontSize: AppFontSize) {
        viewModelScope.launch {
            updateFontSizeUseCase(fontSize)
        }
    }

    fun onAccentColorSelected(accentColor: AppAccentColor) {
        viewModelScope.launch {
            updateAccentColorUseCase(accentColor)
        }
    }

    fun onFullNameChange(value: String) {
        _uiState.update {
            it.copy(fullName = value)
        }
    }

    fun onDateOfBirthChange(value: LocalDate) {
        _uiState.update {
            it.copy(dateOfBirth = value)
        }
    }

    fun onGenderChange(value: Gender) {
        _uiState.update {
            it.copy(gender = value)
        }
    }

    fun onWeightChange(value: String) {
        _uiState.update {
            it.copy(weightKg = value)
                .withBmiPreview()
        }
    }

    fun onHeightChange(value: String) {
        _uiState.update {
            it.copy(heightCm = value)
                .withBmiPreview()
        }
    }

    fun onActivityLevelChange(value: ActivityLevel) {
        _uiState.update {
            it.copy(activityLevel = value)
        }
    }

    fun onGoalChange(value: WeightGoal) {
        _uiState.update {
            it.copy(goal = value)
        }
    }

    fun saveProfile() {
        viewModelScope.launch {
            val profile = buildProfileOrNull()

            if (profile == null) {
                sendValidationMessage()
                return@launch
            }

            _uiState.update {
                it.copy(isProfileSaving = true)
            }

            runCatching {
                saveProfileUseCase(profile)
            }.onSuccess {
                _uiState.update {
                    it.copy(isProfileSaving = false)
                }

                _uiEvent.send(
                    SettingsUiEvent.ShowMessage(
                        UiText.StringResource(R.string.profile_saved)
                    )
                )
            }.onFailure {
                _uiState.update {
                    it.copy(isProfileSaving = false)
                }

                _uiEvent.send(
                    SettingsUiEvent.ShowMessage(
                        UiText.StringResource(R.string.error_save_profile_failed)
                    )
                )
            }
        }
    }

    private fun observeSettings() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        getSettingsUseCase()
            .catch {
                _uiState.update { state ->
                    state.copy(isLoading = false)
                }
            }
            .onEach { settings ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        selectedLanguage = settings.language,
                        selectedThemeMode = settings.themeMode,
                        selectedFontSize = settings.fontSize,
                        selectedAccentColor = settings.accentColor
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val profile = getProfileOnceUseCase()

            profile?.let { existingProfile ->
                _uiState.update {
                    it.copy(
                        fullName = existingProfile.fullName,
                        dateOfBirth = existingProfile.dateOfBirth,
                        gender = existingProfile.gender,
                        weightKg = existingProfile.weightKg.toString(),
                        heightCm = existingProfile.heightCm.toString(),
                        activityLevel = existingProfile.activityLevel,
                        goal = existingProfile.goal
                    ).withBmiPreview()
                }
            }
        }
    }

    private fun SettingsUiState.withBmiPreview(): SettingsUiState {
        val weightKg = weightKg.toDoubleOrNull()
        val heightCm = heightCm.toDoubleOrNull()

        if (
            weightKg == null ||
            heightCm == null ||
            weightKg <= 0 ||
            weightKg > ProfileLimits.MAX_WEIGHT_KG ||
            heightCm < ProfileLimits.MIN_HEIGHT_CM
        ) {
            return copy(
                bmiValue = null,
                bmiCategory = null
            )
        }

        val bmi = bmiCalculator.calculateBmi(
            weightKg = weightKg,
            heightCm = heightCm
        )

        return copy(
            bmiValue = bmi,
            bmiCategory = bmiCalculator.getCategory(bmi)
        )
    }

    private fun buildProfileOrNull(): UserProfile? {
        val state = _uiState.value

        val dateOfBirth = state.dateOfBirth ?: return null
        val gender = state.gender ?: return null
        val activityLevel = state.activityLevel ?: return null
        val goal = state.goal ?: return null

        val weightKg = state.weightKg.toDoubleOrNull() ?: return null
        val heightCm = state.heightCm.toDoubleOrNull() ?: return null

        if (state.fullName.isBlank()) return null
        if (weightKg <= 0 || weightKg > ProfileLimits.MAX_WEIGHT_KG) return null
        if (heightCm < ProfileLimits.MIN_HEIGHT_CM) return null

        return UserProfile(
            id = 1,
            fullName = state.fullName.trim(),
            dateOfBirth = dateOfBirth,
            gender = gender,
            weightKg = weightKg,
            heightCm = heightCm,
            activityLevel = activityLevel,
            goal = goal
        )
    }

    private suspend fun sendValidationMessage() {
        val state = _uiState.value

        val message = when {
            state.fullName.isBlank() -> UiText.StringResource(R.string.error_full_name_required)
            state.dateOfBirth == null -> UiText.StringResource(R.string.error_birth_date_required)
            state.gender == null -> UiText.StringResource(R.string.error_gender_required)
            state.weightKg.toDoubleOrNull() == null ||
                    state.weightKg.toDoubleOrNull()!! <= 0 ||
                    state.weightKg.toDoubleOrNull()!! > ProfileLimits.MAX_WEIGHT_KG -> {
                UiText.StringResource(R.string.error_weight_invalid)
            }
            state.heightCm.toDoubleOrNull() == null ||
                    state.heightCm.toDoubleOrNull()!! < ProfileLimits.MIN_HEIGHT_CM -> {
                UiText.StringResource(R.string.error_height_invalid)
            }
            state.activityLevel == null -> UiText.StringResource(R.string.error_activity_level_required)
            state.goal == null -> UiText.StringResource(R.string.error_goal_required)
            else -> UiText.StringResource(R.string.error_save_profile_failed)
        }

        _uiEvent.send(SettingsUiEvent.ShowMessage(message))
    }
}
