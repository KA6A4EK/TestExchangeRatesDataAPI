package com.example.testexchangeratesdataapi.presentation.navigation

/**
 * Маршруты навигации в приложении.
 */
sealed class NavRoute(val route: String) {
    data object Currencies : NavRoute("currencies")
    data object Favorites : NavRoute("favorites")
    data object Filter : NavRoute("filter")
}
