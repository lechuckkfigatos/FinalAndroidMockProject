package com.example.pftandroidmockproject.presentation.navigation

import androidx.annotation.StringRes
import com.example.pftandroidmockproject.R

sealed class AppDestination(
    val route: String,
    @StringRes val titleRes : Int
) {
    data object Onboarding : AppDestination(
        route = "onboarding",
        titleRes = R.string.onboarding
    )

    data object Dashboard : AppDestination(
        route = "dashboard",
        titleRes = R.string.dashboard
    )

    data object Meals : AppDestination(
        route = "meals",
        titleRes = R.string.meals
    )

    data object Activities : AppDestination(
        route = "activities",
        titleRes = R.string.activities
    )

    data object Statistics : AppDestination(
        route = "statistics",
        titleRes = R.string.statistics
    )

    data object Settings : AppDestination(
        route = "settings",
        titleRes = R.string.settings
    )
}
val bottomNavDestinations = listOf(
    AppDestination.Meals,
    AppDestination.Activities,
    AppDestination.Dashboard,
    AppDestination.Statistics,
    AppDestination.Settings
)
