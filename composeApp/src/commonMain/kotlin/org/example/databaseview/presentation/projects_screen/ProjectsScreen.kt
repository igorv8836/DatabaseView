package org.example.databaseview.presentation.projects_screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import org.example.databaseview.domain.model.*
import org.example.databaseview.presentation.navigation.ProjectDetailsRoute
import org.example.databaseview.presentation.shared_elements.*
import org.example.databaseview.presentation.viewmodel.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProjectsScreen(
    navController: NavHostController, vm: ProjectsViewModel = koinViewModel()
) {
    val state by vm.state.collectAsStateWithLifecycle()

    ProjectsScreen(state = state, navigateToNewProject = {
        navController.navigate(ProjectDetailsRoute(null)) {}
    }) { projectId ->
        navController.navigate(ProjectDetailsRoute(projectId))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(
    state: ProjectsScreenState,
    navigateToNewProject: () -> Unit,
    onProjectSelected: (id: Int) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Проекты") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToNewProject, modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = "Добавить проект"
                )
            }
        }) { paddings ->
        Box(modifier = Modifier.fillMaxSize().padding(paddings)) {
            when (state) {
                is ProjectsScreenState.Loading -> LoadingScreen()
                is ProjectsScreenState.Error -> ErrorScreen(message = state.message)
                is ProjectsScreenState.Success -> ProjectsListScreen(
                    state = state, onProjectSelected = onProjectSelected
                )
            }
        }
    }
}

@Composable
fun ProjectsListScreen(
    state: ProjectsScreenState.Success,
    onProjectSelected: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.projects) { project ->
            ProjectItem(project = project, onProjectSelected = onProjectSelected)
        }
    }
}

@Composable
fun ProjectItem(
    project: ProjectFullModel, onProjectSelected: (id: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(),
        onClick = { onProjectSelected(project.project.id) }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Проект: ${project.project.name}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            val isHuge = (project.project.requirements?.length ?: 0) > 100
            Text(text = "Требования: ${
                if (expanded) project.project.requirements ?: "Нет" else ((project.project.requirements?.take(
                    100
                ) ?: "Нет") + if (isHuge) "..." else "")
            }",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable(enabled = isHuge) { expanded = !expanded })
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Срок: ${project.contract.contract.deadline}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Задачи:", style = MaterialTheme.typography.titleMedium
            )
            LazyColumn(
                modifier = Modifier.heightIn(max = 150.dp)
            ) {
                items(project.tasks) { element ->
                    Text(
                        text = "-${element.status.statusName}- ${element.task.name}: ${element.task.description ?: "Описание отсутствует"}",
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
            project = Project(),
            tasks = listOf(ProjectTaskModel(Task(), Status(), Worker())),
            contract = ContractClientModel(Contract(), Client())
        )
    )
    ProjectsScreen(state = ProjectsScreenState.Success(sampleProjects), navigateToNewProject = {}) {

    }
//    ProjectsScreen(state = ProjectsScreenState.Error("Ошибка загрузки")){}
//    ProjectsScreen(state = ProjectsScreenState.Loading){}
}
