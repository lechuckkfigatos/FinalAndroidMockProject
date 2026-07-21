package com.example.pftandroidmockproject.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pftandroidmockproject.presentation.theme.PFTAndroidMockProjectTheme

@Composable
fun HealthTrackerApp(
    viewModel: AppStartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PFTAndroidMockProjectTheme(
        language = uiState.language,
        themeMode = uiState.themeMode,
        fontSize = uiState.fontSize,
        accentColor = uiState.accentColor
    ) {
        if (uiState.isLoading || uiState.startDestination == null) {
            AppLoadingScreen()
            return@PFTAndroidMockProjectTheme
        }

        val navController = rememberNavController()

        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        val shouldShowBottomBar = bottomNavDestinations.any { destination ->
            destination.route == currentRoute
        }

        Scaffold(
            bottomBar = {
                if (shouldShowBottomBar) {
                    HealthTrackerBottomBar(
                        currentRoute = currentRoute,
                        onDestinationClick = { destination ->
                            navController.navigateToBottomDestination(destination)
                        }
                    )
                }
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                startDestination = uiState.startDestination!!,
                contentPadding = innerPadding
            )
        }
    }
}

@Composable
private fun AppLoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
