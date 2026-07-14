package com.example.pftandroidmockproject.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun HealthTrackerBottomBar(
    currentRoute: String?,
    onDestinationClick: (AppDestination) -> Unit
) {
    NavigationBar {
        bottomNavDestinations.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = {
                    onDestinationClick(destination)
                },
                icon = {
                    Spacer(
                        modifier = Modifier.height(0.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(destination.titleRes)
                    )
                }
            )
        }
    }
}