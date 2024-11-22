package org.example.databaseview.presentation.clients_screen

import androidx.compose.desktop.ui.tooling.preview.Preview
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
import org.example.databaseview.presentation.navigation.ClientDetailsRoute
import org.example.databaseview.presentation.shared_elements.*
import org.example.databaseview.presentation.viewmodel.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ClientsScreen(
    navController: NavHostController, vm: ClientsViewModel = koinViewModel()
) {
    val state by vm.state.collectAsStateWithLifecycle()

    ClientsScreen(state = state, navigateToNewClient = {
        navController.navigate(ClientDetailsRoute(null))
    }) { clientId ->
        navController.navigate(ClientDetailsRoute(clientId))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsScreen(
    state: ClientsScreenState,
    navigateToNewClient: () -> Unit,
    onClientSelected: (id: Int) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Клиенты") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToNewClient, modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = "Добавить клиента"
                )
            }
        }) { paddings ->
        Box(modifier = Modifier.fillMaxSize().padding(paddings)) {
            when (state) {
                is ClientsScreenState.Loading -> LoadingScreen()
                is ClientsScreenState.Error -> ErrorScreen(message = state.message)
                is ClientsScreenState.Success -> ClientsListScreen(
                    state = state, onClientSelected = onClientSelected
                )
            }
        }
    }
}

@Composable
fun ClientsListScreen(
    state: ClientsScreenState.Success,
    onClientSelected: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.clients) { client ->
            ClientItem(client = client, onClientSelected = onClientSelected)
        }
    }
}

@Composable
fun ClientItem(
    client: ClientWithContractsModel, onClientSelected: (id: Int) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(),
        onClick = { onClientSelected(client.client.id) }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = client.client.fullName,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Номер телефона: ${client.client.phone}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Кол-во контрактов: ${client.contracts.size}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Общая сумма контрактов: ${client.contracts.sumOf { it.amount }}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
@Preview
fun ClientsScreenPreview() {
    val sampleClients = listOf(
        ClientWithContractsModel(
            client = Client(),
            contracts = listOf(Contract(), Contract())
        )
    )
    ClientsScreen(state = ClientsScreenState.Success(sampleClients), navigateToNewClient = {}) {

    }
}
