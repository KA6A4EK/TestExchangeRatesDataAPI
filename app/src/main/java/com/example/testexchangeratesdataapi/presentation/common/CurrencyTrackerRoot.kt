package com.example.testexchangeratesdataapi.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.testexchangeratesdataapi.presentation.navigation.CurrencyTrackerNavGraph
import com.example.testexchangeratesdataapi.presentation.navigation.NavRoute

@Composable
fun CurrencyTrackerRoot() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            CurrencyTrackerNavGraph(
                navController = navController,
                startDestination = NavRoute.Currencies.route
            )
        }
    }
}