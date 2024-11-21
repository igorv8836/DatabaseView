package org.example.databaseview.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.*
import org.example.databaseview.presentation.projects_screen.ProjectsScreen
import org.example.databaseview.presentation.projects_screen.projects_details_screen.ProjectDetailsScreen

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

        composable<ProjectDetailsRoute> {
            ProjectDetailsScreen(navController, it.toRoute<ProjectDetailsRoute>().id)
        }

        composable<TaskDetailsRoute> {
            Button(onClick = { navController.popBackStack() }) {
                Text("Go back")
            }
        }
    }
}

