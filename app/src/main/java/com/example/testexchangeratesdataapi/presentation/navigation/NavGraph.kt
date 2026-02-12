package com.example.testexchangeratesdataapi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testexchangeratesdataapi.presentation.currencies.CurrenciesScreen
import com.example.testexchangeratesdataapi.presentation.favorites.FavoritesScreen
import com.example.testexchangeratesdataapi.presentation.filter.FilterScreen

@Composable
fun CurrencyTrackerNavGraph(
    navController: NavHostController,
    startDestination: String = NavRoute.Currencies.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = NavRoute.Currencies.route) {
            CurrenciesScreen(
                onNavigateToFilter = {
                    navController.navigate(NavRoute.Filter.route)
                }
            )
        }

        composable(route = NavRoute.Favorites.route) {
            FavoritesScreen()
        }

        composable(route = NavRoute.Filter.route) {
            FilterScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
