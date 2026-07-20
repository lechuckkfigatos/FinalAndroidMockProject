package com.example.pftandroidmockproject.presentation.profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.activity.ActivityLevel
import com.example.pftandroidmockproject.domain.model.profile.Gender
import com.example.pftandroidmockproject.domain.model.profile.UserProfile
import com.example.pftandroidmockproject.domain.model.setting.WeightGoal
import com.example.pftandroidmockproject.domain.use_case.profile.CalculateBmiUseCase
import com.example.pftandroidmockproject.domain.use_case.profile.CalculateTdeeUseCase
import com.example.pftandroidmockproject.domain.use_case.profile.GetProfileOnceUseCase
import com.example.pftandroidmockproject.domain.use_case.profile.SaveProfileUseCase
import com.example.pftandroidmockproject.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileOnceUseCase: GetProfileOnceUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val calculateBmiUseCase: CalculateBmiUseCase,
    private val calculateTdeeUseCase: CalculateTdeeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadExistingProfile()
    }

    fun onFullNameChange(value : String){
        _uiState.update {
            it.copy(fullName = value)
        }
    }

    fun onDateOfBirthChange(value: LocalDate) {
        _uiState.update {
            it.copy(dateOfBirth = value)
        }
        updateHealthPreview()
    }

    fun onGenderChange(value: Gender) {
        _uiState.update {
            it.copy(gender = value)
        }
        updateHealthPreview()
    }

    fun onWeightChange(value: String) {
        _uiState.update {
            it.copy(weightKg = value)
        }
        updateHealthPreview()
    }

    fun onHeightChange(value: String) {
        _uiState.update {
            it.copy(heightCm = value)
        }
        updateHealthPreview()
    }

    fun onActivityLevelChange(value: ActivityLevel) {
        _uiState.update {
            it.copy(activityLevel = value)
        }
        updateHealthPreview()
    }

    fun onGoalChange(value: WeightGoal) {
        _uiState.update {
            it.copy(goal = value)
        }
        updateHealthPreview()
    }
    fun saveProfile() {
        viewModelScope.launch {
            val profile = buildProfileOrNull()

            if (profile == null) {
                sendValidationMessage()
                return@launch
            }

            _uiState.update {
                it.copy(isSaving = true)
            }

            try {
                saveProfileUseCase(profile)

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        hasExistingProfile = true
                    )
                }

                _uiEvent.send(
                    ProfileUiEvent.ShowMessage(
                        UiText.StringResource(R.string.profile_saved)
                    )
                )

                _uiEvent.send(ProfileUiEvent.SaveSuccess)
            } catch (e: IllegalArgumentException) {
                _uiState.update {
                    it.copy(isSaving = false)
                }

                _uiEvent.send(
                    ProfileUiEvent.ShowMessage(
                        UiText.StringResource(R.string.error_save_profile_failed)
                    )
                )
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isSaving = false)
                }

                _uiEvent.send(
                    ProfileUiEvent.ShowMessage(
                        UiText.StringResource(R.string.error_save_profile_failed)
                    )
                )
            }
        }
    }

    //nếu k phải ng dùng cũ thì dừng coroutine, cho họ điền thông tin
    private fun loadExistingProfile() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            val profile = getProfileOnceUseCase()

            if (profile == null) {
                _uiState.update {
                    it.copy(isLoading = false)
                }
                return@launch
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    hasExistingProfile = true,
                    fullName = profile.fullName,
                    dateOfBirth = profile.dateOfBirth,
                    gender = profile.gender,
                    weightKg = profile.weightKg.toString(),
                    heightCm = profile.heightCm.toString(),
                    activityLevel = profile.activityLevel,
                    goal = profile.goal
                )
            }

            updateHealthPreview()
        }
    }

    //tính real time tdee và bmi nếu người dùng nhập đủ
    private fun updateHealthPreview() {
        val profile = buildProfileOrNull() ?: run {
            _uiState.update {
                it.copy(
                    tdeeCalories = null,
                    bmiValue = null,
                    bmiCategory = null
                )
            }
            return
        }

        val tdeeCalories = calculateTdeeUseCase(profile)
        val bmiResult = calculateBmiUseCase(profile)

        _uiState.update {
            it.copy(
                tdeeCalories = tdeeCalories,
                bmiValue = bmiResult.value,
                bmiCategory = bmiResult.category
            )
        }
    }

    //validation
    private fun buildProfileOrNull(): UserProfile? {
        val state = _uiState.value

        val dateOfBirth = state.dateOfBirth ?: return null
        val gender = state.gender ?: return null
        val activityLevel = state.activityLevel ?: return null
        val goal = state.goal ?: return null

        val weightKg = state.weightKg.toDoubleOrNull() ?: return null
        val heightCm = state.heightCm.toDoubleOrNull() ?: return null

        if (state.fullName.isBlank()) return null
        if (weightKg <= 0) return null
        if (heightCm <= 0) return null

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
            state.fullName.isBlank() -> {
                UiText.StringResource(R.string.error_full_name_required)
            }

            state.dateOfBirth == null -> {
                UiText.StringResource(R.string.error_birth_date_required)
            }

            state.gender == null -> {
                UiText.StringResource(R.string.error_gender_required)
            }

            state.weightKg.toDoubleOrNull() == null ||
                    state.weightKg.toDoubleOrNull()!! <= 0 -> {
                UiText.StringResource(R.string.error_weight_invalid)
            }

            state.heightCm.toDoubleOrNull() == null ||
                    state.heightCm.toDoubleOrNull()!! <= 0 -> {
                UiText.StringResource(R.string.error_height_invalid)
            }

            state.activityLevel == null -> {
                UiText.StringResource(R.string.error_activity_level_required)
            }

            state.goal == null -> {
                UiText.StringResource(R.string.error_goal_required)
            }

            else -> {
                UiText.StringResource(R.string.error_save_profile_failed)
            }
        }

        _uiEvent.send(ProfileUiEvent.ShowMessage(message))
    }
}
