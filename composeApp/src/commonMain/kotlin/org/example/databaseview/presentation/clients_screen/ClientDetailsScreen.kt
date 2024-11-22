package org.example.databaseview.presentation.clients_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.orbit_mvi.compose.*
import org.example.databaseview.presentation.projects_screen.projects_details_screen.DeleteConfirmationDialog
import org.example.databaseview.presentation.shared_elements.LoadingScreen
import org.example.databaseview.presentation.viewmodel.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ClientDetailsScreen(
    navController: NavController, clientId: Int?, vm: ClientDetailsViewModel = koinViewModel()
) {
    val state by vm.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    vm.collectSideEffect {
        when (it) {
            is ClientDetailsEffect.ShowError -> {
                snackBarHostState.showSnackbar(it.message)
            }

            ClientDetailsEffect.NavigateToLastScreen -> {
                navController.popBackStack()
            }
        }
    }

    LaunchedEffect(clientId) { vm.onEvent(ClientDetailsEvent.LoadClient(clientId)) }

    if (state.isLoading) {
        LoadingScreen()
    } else {
        ClientDetailsScreen(
            state = state,
            isCreation = clientId == null,
            snackBarHostState = snackBarHostState,
            onEvent = vm::onEvent,
            editContracts = {
                TODO()
            },
            createContract = { TODO() },
            popBackStack = { navController.popBackStack() }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDetailsScreen(
    state: ClientDetailsScreenState,
    isCreation: Boolean,
    snackBarHostState: SnackbarHostState,
    editContracts: () -> Unit,
    createContract: () -> Unit,
    onEvent: (ClientDetailsEvent) -> Unit,
    popBackStack: () -> Unit = {}
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    DeleteConfirmationDialog(
        showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        onConfirm = {
            showDeleteDialog = false
            onEvent(ClientDetailsEvent.DeleteClient)
        })

    Scaffold(topBar = {
        TopAppBar(title = { Text("Детали клиента") }, navigationIcon = {
            IconButton(onClick = popBackStack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
            }
        }, actions = {
            IconButton(
                onClick = {
                    onEvent(if (isCreation) ClientDetailsEvent.CreateClient else ClientDetailsEvent.SaveClient)
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
                        createContract = createContract,
                        showDeleteDialog = { showDeleteDialog = true },
                        onEvent = onEvent
                    )
                }
                items(state.contracts) { task ->
                    ContractItem(
                        element = task,
                        onClick = editContracts
                    )
                }
            }
        }
    }
}

@Composable
private fun MainContent(
    state: ClientDetailsScreenState,
    isCreation: Boolean,
    showDeleteDialog: () -> Unit,
    createContract: () -> Unit,
    onEvent: (ClientDetailsEvent) -> Unit
) {
    OutlinedTextField(
        value = state.client.fullName,
        onValueChange = { onEvent(ClientDetailsEvent.EnterClientName(it)) },
        label = { Text("Имя клиента") },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = state.client.phone,
        onValueChange = {
            if (it.all { char -> char.isDigit() }) {
                onEvent(ClientDetailsEvent.EnterClientPhone(it))
            }
        },
        label = { Text("Номер телефона клиента") },
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )

    if (!isCreation) {
        Button(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors()
                .copy(containerColor = MaterialTheme.colorScheme.error),
            onClick = showDeleteDialog
        ) {
            Text("Удалить")
        }
    }

    Text(
        "Контракты",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )

    Button(
        onClick = createContract,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Создать новый контракт")
    }
}