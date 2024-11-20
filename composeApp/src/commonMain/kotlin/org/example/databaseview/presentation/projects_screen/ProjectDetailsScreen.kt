package org.example.databaseview.presentation.projects_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.example.databaseview.domain.model.*
import org.example.databaseview.presentation.viewmodel.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProjectDetailsScreen(
    navController: NavController,
    vm: ProjectsViewModel = koinViewModel()
) {
    val state by vm.fullProject.collectAsStateWithLifecycle()
    val contracts by vm.contracts.collectAsStateWithLifecycle()

    ProjectDetailsScreen(
        state = state,
        contracts = contracts,
        onEvent = vm::onEvent,
        popBackStack = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailsScreen(
    state: ProjectFullModel,
    contracts: List<Contract>,
    onEvent: (ProjectsEvent) -> Unit,
    popBackStack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали проекта") },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(
                value = state.project.name,
                onValueChange = { onEvent(ProjectsEvent.EnterProjectName(it)) },
                label = { Text("Название проекта") }
            )

            OutlinedTextField(
                value = state.project.requirements ?: "",
                onValueChange = { onEvent(ProjectsEvent.EnterProjectRequirements(it)) },
                label = { Text("Описание проекта") },
                modifier = Modifier.padding(top = 8.dp)
            )

            var expanded by remember { mutableStateOf(false) }
            var selectedContract by remember { mutableStateOf(state.contract) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedContract.id.toString(),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Контракт") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .clickable { expanded = true }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    LazyColumn(
                        modifier = Modifier.width(400.dp).height(200.dp)
                    ) {
                        items(contracts) { contract ->
                            DropdownMenuItem(
                                text = { Text("ID: ${contract.id} - Срок: ${contract.deadline}") },
                                onClick = {
                                    selectedContract = contract
                                    onEvent(ProjectsEvent.SelectContract(contract))
                                    expanded = false
                                }
                            )
                        }
                    }

                    HorizontalDivider()
                    DropdownMenuItem(
                        onClick = { onEvent(ProjectsEvent.CreateNewContract) },
                        text = {
                            Text(
                                "Создать новый контракт",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                }
            }

            Row(modifier = Modifier.padding(top = 8.dp)) {
                Button(
                    onClick = { onEvent(ProjectsEvent.EditContract(selectedContract)) }
                ) {
                    Text("Редактировать контракт")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onEvent(ProjectsEvent.DeleteContract(selectedContract.id)) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                ) {
                    Text("Удалить контракт")
                }
            }

            Text(
                "Задачи",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.tasks) { task ->
                    TaskItem(task = task.first, status = task.second, onClick = {
//                        navController.navigate("taskDetails/${task.first.id}")
                    })
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, status: Status, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Column {
            Text("Задача: ${task.name}", style = MaterialTheme.typography.titleSmall)
            Text("Статус: ${status.statusName}", style = MaterialTheme.typography.bodyMedium)
            Text("Дедлайн: ${task.deadlineDate}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}