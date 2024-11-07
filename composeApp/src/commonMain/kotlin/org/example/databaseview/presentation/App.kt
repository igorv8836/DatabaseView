package org.example.databaseview.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.example.databaseview.presentation.navigation.*

@Composable
fun App() {
    val navController = rememberNavController()
    MaterialTheme {
        Scaffold(
            bottomBar = { AppBottomNavigation(navController) }
        ) {
            Box(modifier = Modifier.padding(it)) {
                AppNavGraph(navController)
            }
        }
    }
}