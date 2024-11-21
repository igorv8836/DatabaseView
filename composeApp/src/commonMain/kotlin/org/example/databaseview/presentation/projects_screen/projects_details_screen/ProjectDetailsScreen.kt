package org.example.databaseview.presentation.projects_screen.projects_details_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.orbit_mvi.compose.*
import org.example.databaseview.presentation.navigation.*
import org.example.databaseview.presentation.shared_elements.LoadingScreen
import org.example.databaseview.presentation.viewmodel.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProjectDetailsScreen(
    navController: NavController, projectId: Int?, vm: ProjectDetailsViewModel = koinViewModel()
) {
    val state by vm.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    vm.collectSideEffect {
        when (it) {
            is ProjectDetailsEffect.ShowError -> {
                snackBarHostState.showSnackbar(it.message)
            }

            ProjectDetailsEffect.NavigateToLastScreen -> {
                navController.popBackStack()
            }
        }
    }

    LaunchedEffect(projectId) { vm.onEvent(ProjectDetailsEvent.LoadProject(projectId)) }

    if (state.isLoading) {
        LoadingScreen()
    } else {
        ProjectDetailsScreen(
            state = state,
            isCreation = projectId == null,
            snackBarHostState = snackBarHostState,
            createNewTask = { navController.navigate(TaskDetailsRoute(null)) },
            openTask = { id -> navController.navigate(TaskDetailsRoute(id)) },
            onEvent = vm::onEvent,
            editContracts = {
                navController.navigate(ClientsRoute)
            },
            popBackStack = { navController.popBackStack() }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailsScreen(
    state: ProjectDetailsScreenState,
    isCreation: Boolean,
    snackBarHostState: SnackbarHostState,
    editContracts: () -> Unit,
    createNewTask: () -> Unit = {},
    openTask: (Int) -> Unit = {},
    onEvent: (ProjectDetailsEvent) -> Unit,
    popBackStack: () -> Unit = {}
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    DeleteConfirmationDialog(showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        onConfirm = {
            showDeleteDialog = false
            onEvent(ProjectDetailsEvent.DeleteProject)
        })

    Scaffold(topBar = {
        TopAppBar(title = { Text("Детали проекта") }, navigationIcon = {
            IconButton(onClick = popBackStack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
            }
        }, actions = {
            IconButton(
                onClick = {
                    onEvent(if (isCreation) ProjectDetailsEvent.CreateProject else ProjectDetailsEvent.SaveProject)
                },
            ) { Icon(imageVector = Icons.Default.Check, contentDescription = "Сохранить") }
        })
    }, snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { values ->
        Column(
            modifier = Modifier.padding(values).padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    MainContent(
                        state,
                        isCreation = isCreation,
                        createNewTask = createNewTask,
                        editContracts = editContracts,
                        showDialog = { showDeleteDialog = true },
                        onEvent = onEvent
                    )
                }
                items(state.expandedProject.tasks) { task ->
                    TaskItem(
                        element = task,
                        onClick = { openTask(task.task.id) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainContent(
    state: ProjectDetailsScreenState,
    isCreation: Boolean,
    createNewTask: () -> Unit = {},
    showDialog: () -> Unit,
    editContracts: () -> Unit,
    onEvent: (ProjectDetailsEvent) -> Unit
) {
    OutlinedTextField(
        value = state.expandedProject.project.name,
        onValueChange = { onEvent(ProjectDetailsEvent.EnterProjectName(it)) },
        label = { Text("Название проекта") },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = state.expandedProject.project.requirements ?: "",
        onValueChange = { onEvent(ProjectDetailsEvent.EnterProjectRequirements(it)) },
        label = { Text("Описание проекта") },
        modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
    )
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(top = 8.dp)
    ) {
        OutlinedTextField(value = state.expandedProject.contract.getDescription(),
            onValueChange = {},
            readOnly = true,
            label = { Text("Контракт") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .clickable { expanded = true })

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            LazyColumn(modifier = Modifier.width(400.dp).height(200.dp)) {
                items(state.contracts) { contract ->
                    DropdownMenuItem(
                        text = { Text(contract.getDescription()) },
                        onClick = {
                            onEvent(ProjectDetailsEvent.SelectContract(contract))
                            expanded = false
                        }
                    )
                }
            }
            HorizontalDivider()
            DropdownMenuItem(
                onClick = editContracts,
                text = {
                    Text("Редактировать контракты", color = MaterialTheme.colorScheme.primary)
                }
            )
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
    ) {
        if (!isCreation) {
            Button(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors()
                    .copy(containerColor = MaterialTheme.colorScheme.error),
                onClick = showDialog
            ) {
                Text("Удалить")
            }
        }
    }

    Text(
        "Задачи",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )

    Button(
        onClick = createNewTask,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Создать новую задачу")
    }
}