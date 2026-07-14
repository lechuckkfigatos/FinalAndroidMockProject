package com.example.pftandroidmockproject.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pftandroidmockproject.presentation.profile.OnboardingScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    contentPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(contentPadding)
    ) {
        composable(
            route = AppDestination.Onboarding.route
        ) {
            OnboardingScreen(
                onProfileSaved = {
                    navController.navigate(AppDestination.Dashboard.route) {
                        popUpTo(AppDestination.Onboarding.route) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = AppDestination.Dashboard.route
        ) {
            PlaceholderScreen(
                destination = AppDestination.Dashboard
            )
        }

        composable(
            route = AppDestination.Meals.route
        ) {
            PlaceholderScreen(
                destination = AppDestination.Meals
            )
        }

        composable(
            route = AppDestination.Activities.route
        ) {
            PlaceholderScreen(
                destination = AppDestination.Activities
            )
        }

        composable(
            route = AppDestination.Statistics.route
        ) {
            PlaceholderScreen(
                destination = AppDestination.Statistics
            )
        }

        composable(
            route = AppDestination.Settings.route
        ) {
            PlaceholderScreen(
                destination = AppDestination.Settings
            )
        }
    }
}

@Composable
private fun PlaceholderScreen(
    destination: AppDestination
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(destination.titleRes),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}