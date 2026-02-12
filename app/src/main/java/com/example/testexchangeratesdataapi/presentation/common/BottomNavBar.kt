package com.example.testexchangeratesdataapi.presentation.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.testexchangeratesdataapi.R
import com.example.testexchangeratesdataapi.presentation.navigation.NavRoute
import com.example.testexchangeratesdataapi.ui.theme.BlueMain
import com.example.testexchangeratesdataapi.ui.theme.Lavender


@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Показываем нижнюю панель только на основных экранах (не на Filter)
    val showBottomBar = currentDestination?.route in listOf(
        NavRoute.Currencies.route,
        NavRoute.Favorites.route
    )

    fun isSelected(route: String) = currentDestination?.hierarchy?.any { it.route == route } == true

    if (showBottomBar) {
        NavigationBar {
            NavigationBarItem(
                selected = isSelected(NavRoute.Currencies.route),
                onClick = {
                    navController.navigate(NavRoute.Currencies.route) {
                        // Очищаем back stack до Currencies, чтобы избежать накопления экранов
                        popUpTo(NavRoute.Currencies.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painterResource(R.drawable.currencies),
                        contentDescription = null,
                        tint = if (isSelected(NavRoute.Currencies.route)) BlueMain else Lavender
                    )
                },
                label = { Text(text = "Currencies") },

                )
            NavigationBarItem(
                selected = isSelected(NavRoute.Favorites.route),
                onClick = {
                    navController.navigate(NavRoute.Favorites.route) {
                        popUpTo(NavRoute.Currencies.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painterResource(R.drawable.favorites),
                        contentDescription = null,
                        tint = if (isSelected(NavRoute.Favorites.route)) BlueMain else Lavender
                    )
                },
                label = { Text(text = "Favorites") }
            )
        }
    }
}