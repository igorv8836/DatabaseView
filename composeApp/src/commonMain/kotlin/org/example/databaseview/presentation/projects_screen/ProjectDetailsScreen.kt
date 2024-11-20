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
    projectId: Int?,
    vm: ProjectsViewModel = koinViewModel()
) {
    val state by vm.fullProject.collectAsStateWithLifecycle()
    val contracts by vm.contractClientModels.collectAsStateWithLifecycle()

    LaunchedEffect(projectId){
        projectId?.let {
            vm.onEvent(ProjectsEvent.LoadProject(it))
        }
    }

    ProjectDetailsScreen(
        state = state,
        contracts = ContractsList(contracts),
        onEvent = vm::onEvent,
        popBackStack = { navController.popBackStack() })
}

@Stable
data class ContractsList(
    val data: List<ContractClientModel>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailsScreen(
    state: ProjectFullModel,
    contracts: ContractsList,
    onEvent: (ProjectsEvent) -> Unit,
    popBackStack: () -> Unit = {}
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Детали проекта") }, navigationIcon = {
            IconButton(onClick = popBackStack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
            }
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            ScaffoldContent(
                state = state, contracts = contracts, onEvent = onEvent
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScaffoldContent(
    state: ProjectFullModel, contracts: ContractsList, onEvent: (ProjectsEvent) -> Unit
) {
    OutlinedTextField(
        value = state.project.name,
        onValueChange = { onEvent(ProjectsEvent.EnterProjectName(it)) },
        label = { Text("Название проекта") },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = state.project.requirements ?: "",
        onValueChange = { onEvent(ProjectsEvent.EnterProjectRequirements(it)) },
        label = { Text("Описание проекта") },
        modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedContract by remember { mutableStateOf(state.contract) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(top = 8.dp)
    ) {
        OutlinedTextField(value = selectedContract.contract.id.toString(),
            onValueChange = {},
            readOnly = true,
            label = { Text("Контракт") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .clickable { expanded = true })

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            LazyColumn(
                modifier = Modifier.width(400.dp).height(200.dp)
            ) {
                items(contracts.data) { contract ->
                    DropdownMenuItem(text = {
                        Text("ID: ${contract.contract.id} - " +
                                "${contract.client.fullName} - " +
                                "Срок: ${contract.contract.deadline}")
                    },
                        onClick = {
                            selectedContract = contract
                            onEvent(ProjectsEvent.SelectContract(contract.contract))
                            expanded = false
                            TODO()
                        })
                }
            }

            HorizontalDivider()
            DropdownMenuItem(onClick = {
                onEvent(ProjectsEvent.CreateNewContract)
                TODO()
            }, text = {
                Text(
                    "Редактировать контракты", color = MaterialTheme.colorScheme.primary
                )
            })
        }
    }

    Text(
        "Задачи",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.tasks) { task ->
            TaskItem(element = task, onClick = {
//                        navController.navigate("taskDetails/${task.first.id}")
            })
        }
    }
}

@Composable
fun TaskItem(element: ProjectTaskModel, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Column {
            Text("Задача: ${element.task.name}", style = MaterialTheme.typography.titleSmall)
            Text(
                "Статус: ${element.status.statusName}", style = MaterialTheme.typography.bodyMedium
            )
            Text("Дедлайн: ${element.task.name}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}