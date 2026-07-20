package com.example.pftandroidmockproject.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode
import com.example.pftandroidmockproject.domain.use_case.setting.GetSettingsUseCase
import com.example.pftandroidmockproject.domain.use_case.setting.UpdateLanguageUseCase
import com.example.pftandroidmockproject.domain.use_case.setting.UpdateThemeModeUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateThemeModeUseCase: UpdateThemeModeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        observeSettings()
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
                        selectedThemeMode = settings.themeMode
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}