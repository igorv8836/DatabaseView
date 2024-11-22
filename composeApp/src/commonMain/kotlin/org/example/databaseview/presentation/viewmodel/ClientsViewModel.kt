package org.example.databaseview.presentation.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import org.example.databaseview.domain.model.ClientWithContractsModel
import org.example.databaseview.domain.repository.ClientRepository
import org.example.databaseview.utils.*

class ClientsViewModel(
    clientRepository: ClientRepository
) : ViewModel() {
    val state = clientRepository.getClientsWithAllContracts().asResult().map {
        when (it) {
            is MyResult.Success -> ClientsScreenState.Success(it.data)
            is MyResult.Error -> ClientsScreenState.Error(it.exception.message ?: "Unknown error")
            is MyResult.Loading -> ClientsScreenState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = ClientsScreenState.Loading
    )
}


@Stable
sealed interface ClientsScreenState {
    data class Success(val clients: List<ClientWithContractsModel>) : ClientsScreenState
    data class Error(val message: String) : ClientsScreenState
    data object Loading : ClientsScreenState
}