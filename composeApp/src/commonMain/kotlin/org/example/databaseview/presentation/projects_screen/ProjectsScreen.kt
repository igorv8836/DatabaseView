package org.example.databaseview.presentation.projects_screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import org.example.databaseview.domain.model.*
import org.example.databaseview.presentation.navigation.ProjectDetailsRoute
import org.example.databaseview.presentation.viewmodel.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProjectsScreen(
    navController: NavHostController,
    vm: ProjectsViewModel = koinViewModel()
) {
    val state by vm.state.collectAsStateWithLifecycle()

    ProjectsScreen(state = state, navigateToNewProject = {
        navController.navigate(ProjectDetailsRoute(null)) {}
    }) { projectId ->
        navController.navigate(ProjectDetailsRoute(projectId))
    }
}

@Composable
fun ProjectsScreen(
    state: ProjectsScreenState,
    navigateToNewProject: () -> Unit,
    onProjectSelected: (id: Int) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToNewProject,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Добавить проект"
                )
            }
        }
    ) {
        when (state) {
            is ProjectsScreenState.Loading -> LoadingScreen()
            is ProjectsScreenState.Error -> ErrorScreen(message = state.message)
            is ProjectsScreenState.Success -> ProjectsListScreen(
                projects = state.projects,
                onProjectSelected = onProjectSelected
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "Ошибка: $message",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun ProjectsListScreen(
    projects: List<ProjectFullModel>,
    onProjectSelected: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(projects) { project ->
            ProjectItem(project = project, onProjectSelected = onProjectSelected)
        }
    }
}

@Composable
fun ProjectItem(
    project: ProjectFullModel,
    onProjectSelected: (id: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(),
        onClick = { onProjectSelected(project.project.id) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Проект: ${project.project.name}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            val isHuge = (project.project.requirements?.length ?: 0) > 100
            Text(
                text = "Требования: ${
                    if (expanded) project.project.requirements ?: "Нет" else ((project.project.requirements?.take(
                        100
                    ) ?: "Нет") + if (isHuge) "..." else "")
                }",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable(enabled = isHuge) { expanded = !expanded }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Срок: ${project.contract.deadline}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Задачи:",
                style = MaterialTheme.typography.titleMedium
            )
            LazyColumn(
                modifier = Modifier.heightIn(max = 150.dp)
            ) {
                items(project.tasks) { task ->
                    Text(
                        text = "-${task.second.statusName}- ${task.first.name}: ${task.first.description ?: "Описание отсутствует"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ProjectsScreenPreview() {
    val sampleProjects = listOf(
        ProjectFullModel(
            project = Project(
                id = 1,
                name = "Проект А",
                requirements = "Требования А",
                contractId = 1
            ),
            tasks = listOf(
                Pair(
                    Task(
                        id = 1,
                        projectId = 1,
                        name = "Задача 1",
                        description = "Описание задачи 1",
                        deadlineDate = java.time.LocalDate.now(),
                        statusId = 1,
                        authorId = 1
                    ),
                    Status(
                        id = 1,
                        statusName = "Выполнено"
                    )
                )
            ),
            contract = Contract(
                id = 1,
                clientId = 1,
                amount = 1000.0,
                deadline = java.time.LocalDate.now().plusDays(30)
            )
        )
    )
    ProjectsScreen(
        state = ProjectsScreenState.Success(sampleProjects),
        navigateToNewProject = {}
        ) {

    }
//    ProjectsScreen(state = ProjectsScreenState.Error("Ошибка загрузки")){}
//    ProjectsScreen(state = ProjectsScreenState.Loading){}
}
