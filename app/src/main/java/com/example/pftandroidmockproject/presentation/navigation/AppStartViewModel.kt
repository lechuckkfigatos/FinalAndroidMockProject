package com.example.pftandroidmockproject.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pftandroidmockproject.data.local.seed.DatabaseSeeder
import com.example.pftandroidmockproject.domain.use_case.profile.GetProfileOnceUseCase
import com.example.pftandroidmockproject.domain.use_case.setting.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppStartViewModel @Inject constructor(
    private val databaseSeeder: DatabaseSeeder,
    private val getProfileOnceUseCase: GetProfileOnceUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppStartUiState())
    val uiState: StateFlow<AppStartUiState> = _uiState.asStateFlow()

    init {
        observeSettings()
        prepareApp()
    }

    private fun observeSettings() {
        getSettingsUseCase()
            .onEach { settings ->
                _uiState.update {
                    it.copy(
                        language = settings.language,
                        themeMode = settings.themeMode,
                        fontSize = settings.fontSize,
                        accentColor = settings.accentColor
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun prepareApp() {
        viewModelScope.launch {
            databaseSeeder.seedIfNeeded()

            val profile = getProfileOnceUseCase()

            val startDestination = if (profile == null) {
                AppDestination.Onboarding.route
            } else {
                AppDestination.Dashboard.route
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    startDestination = startDestination
                )
            }
        }
    }
}
