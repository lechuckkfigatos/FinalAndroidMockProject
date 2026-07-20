package com.example.pftandroidmockproject.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundBottom
import com.example.pftandroidmockproject.presentation.theme.HealthGreen
import com.example.pftandroidmockproject.presentation.theme.HealthGreenSoft
import com.example.pftandroidmockproject.presentation.theme.HealthSecondaryText

@Composable
fun HealthTrackerBottomBar(
    currentRoute: String?,
    onDestinationClick: (AppDestination) -> Unit
) {
    NavigationBar(
        containerColor = HealthBackgroundBottom,
        tonalElevation = NavigationBarDefaultsElevation
    ) {
        bottomNavDestinations.forEach { destination ->
            val selected = currentRoute == destination.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    onDestinationClick(destination)
                },
                icon = {
                    Icon(
                        imageVector = destination.icon(),
                        contentDescription = stringResource(destination.titleRes)
                    )
                },
                label = {
                    Text(
                        text = stringResource(destination.titleRes)
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = HealthGreen,
                    selectedTextColor = HealthGreen,
                    indicatorColor = HealthGreenSoft,
                    unselectedIconColor = HealthSecondaryText,
                    unselectedTextColor = HealthSecondaryText
                )
            )
        }
    }
}

private val NavigationBarDefaultsElevation = 4.dp

private fun AppDestination.icon(): ImageVector {
    return when (this) {
        AppDestination.Meals -> Icons.Filled.Restaurant
        AppDestination.Activities -> Icons.AutoMirrored.Filled.DirectionsRun
        AppDestination.Dashboard -> Icons.Filled.Home
        AppDestination.Statistics -> Icons.Filled.BarChart
        AppDestination.Settings -> Icons.Filled.Settings
        AppDestination.Onboarding -> Icons.Filled.Home
    }
}
