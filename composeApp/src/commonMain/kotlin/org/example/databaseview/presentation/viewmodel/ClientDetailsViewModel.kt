package org.example.databaseview.presentation.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import org.example.databaseview.domain.model.*
import org.example.databaseview.domain.repository.ClientRepository
import org.orbitmvi.orbit.ContainerHost

class ClientDetailsViewModel(
    private val clientRepository: ClientRepository
) : ContainerHost<ClientDetailsScreenState, ClientDetailsEffect>, ViewModel() {
    override val container =
        container<ClientDetailsScreenState, ClientDetailsEffect>(ClientDetailsScreenState())

    fun onEvent(event: ClientDetailsEvent) {
        when (event) {
            ClientDetailsEvent.CreateClient -> intent {
                clientRepository.createClient(state.client).exceptionOrNull()?.let {
                    postSideEffect(ClientDetailsEffect.ShowError(it.message ?: "Unknown error"))
                } ?: postSideEffect(ClientDetailsEffect.NavigateToLastScreen)
            }

            ClientDetailsEvent.DeleteClient -> intent {
                clientRepository.deleteClient(state.client.id).exceptionOrNull()?.let {
                    postSideEffect(ClientDetailsEffect.ShowError(it.message ?: "Unknown error"))
                } ?: postSideEffect(ClientDetailsEffect.NavigateToLastScreen)
            }

            is ClientDetailsEvent.EnterClientName -> blockingIntent {
                reduce { state.copy(client = state.client.copy(fullName = event.newVal)) }
            }

            is ClientDetailsEvent.EnterClientPhone -> blockingIntent {
                reduce { state.copy(client = state.client.copy(phone = event.newVal)) }
            }

            is ClientDetailsEvent.LoadClient -> intent {
                if (event.id != null) {
                    clientRepository.getClientWithContracts(event.id).fold(
                        onFailure = {
                            postSideEffect(
                                ClientDetailsEffect.ShowError(it.message ?: "Unknown error")
                            )
                        },
                        onSuccess = { data ->
                            reduce {
                                state.copy(
                                    isLoading = false,
                                    client = data.client,
                                    contracts = data.contracts
                                )
                            }
                        }
                    )
                } else {
                    reduce { state.copy(isLoading = false) }
                }
            }

            ClientDetailsEvent.SaveClient -> intent {
                clientRepository.updateClient(state.client).exceptionOrNull()?.let {
                    postSideEffect(ClientDetailsEffect.ShowError(it.message ?: "Unknown error"))
                } ?: postSideEffect(ClientDetailsEffect.NavigateToLastScreen)
            }

            is ClientDetailsEvent.SelectContract -> TODO()
        }
    }

}


sealed interface ClientDetailsEvent {
    data object SaveClient : ClientDetailsEvent
    data class LoadClient(val id: Int?) : ClientDetailsEvent // creating or loading existing client
    data object CreateClient : ClientDetailsEvent
    data object DeleteClient : ClientDetailsEvent
    data class EnterClientName(val newVal: String) : ClientDetailsEvent
    data class EnterClientPhone(val newVal: String) : ClientDetailsEvent
    data class SelectContract(val contract: ContractClientModel) : ClientDetailsEvent
}


@Immutable
data class ClientDetailsScreenState(
    val isLoading: Boolean = true,
    val client: Client = Client(),
    val contracts: List<Contract> = emptyList(),
)

sealed interface ClientDetailsEffect {
    data class ShowError(val message: String) : ClientDetailsEffect
    data object NavigateToLastScreen : ClientDetailsEffect
}
