package com.example.pftandroidmockproject.presentation.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToBottomDestination(
    destination: AppDestination
) {
    val currentRoute = currentBackStackEntry?.destination?.route

    if (currentRoute == destination.route) {
        return
    }

    /*
     * Khi bấm Dashboard, ưu tiên pop về Dashboard cũ trong back stack.
     * Ko có Dashboard trong back stack thì navigate mới.
     */
    if (destination == AppDestination.Dashboard) {
        val popped = popBackStack(
            route = AppDestination.Dashboard.route,
            inclusive = false
        )

        if (!popped) {
            navigate(AppDestination.Dashboard.route) {
                launchSingleTop = true
            }
        }

        return
    }

    navigate(destination.route) {
        launchSingleTop = true
        restoreState = true

        popUpTo(AppDestination.Dashboard.route) {
            saveState = true
        }
    }
}