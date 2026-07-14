package com.example.pftandroidmockproject.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pftandroidmockproject.domain.use_case.profile.GetProfileOnceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppStartViewModel @Inject constructor(
    private val getProfileOnceUseCase: GetProfileOnceUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppStartUiState())
    val uiState: StateFlow<AppStartUiState> = _uiState.asStateFlow()

    init {
        checkStartDestination()
    }

    private fun checkStartDestination() {
        viewModelScope.launch {
            val profile = getProfileOnceUseCase()

            val startDestination = if(profile == null){
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