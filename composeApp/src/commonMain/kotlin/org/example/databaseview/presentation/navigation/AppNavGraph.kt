package org.example.databaseview.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.*
import org.example.databaseview.presentation.projects_screen.*

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

