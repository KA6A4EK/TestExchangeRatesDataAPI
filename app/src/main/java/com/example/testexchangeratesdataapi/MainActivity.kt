package com.example.testexchangeratesdataapi

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.testexchangeratesdataapi.presentation.navigation.CurrencyTrackerNavGraph
import com.example.testexchangeratesdataapi.presentation.navigation.NavRoute
import com.example.testexchangeratesdataapi.ui.theme.TestExchangeRatesDataAPITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestExchangeRatesDataAPITheme {
                CurrencyTrackerRoot()
            }
        }
    }
}

@Composable
fun CurrencyTrackerRoot() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CurrencyTrackerNavGraph(
                navController = navController,
                startDestination = NavRoute.Currencies.route
            )
        }
    }
}

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

    if (showBottomBar) {
        NavigationBar {
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == NavRoute.Currencies.route } == true,
                onClick = {
                    navController.navigate(NavRoute.Currencies.route) {
                        // Очищаем back stack до Currencies, чтобы избежать накопления экранов
                        popUpTo(NavRoute.Currencies.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                icon = { Icon(Icons.Default.Home,contentDescription = null,) },
                label = { Text(text = "Currencies") },
            )
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == NavRoute.Favorites.route } == true,
                onClick = {
                    navController.navigate(NavRoute.Favorites.route) {
                        popUpTo(NavRoute.Currencies.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                icon = { Icon(Icons.Default.Star,contentDescription = null,) },
                label = { Text(text = "Favorites") }
            )
        }
    }
}