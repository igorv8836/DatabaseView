package org.example.databaseview.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import org.example.databaseview.presentation.projects_screen.ProjectsScreen

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ProjectsRoute,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<ProjectsRoute> {
            ProjectsScreen(navController)
        }
        composable<WorkersRoute> {
            Text("Workers")
        }
        composable<ClientsRoute> {
            Text("Clients")
        }
    }
}

