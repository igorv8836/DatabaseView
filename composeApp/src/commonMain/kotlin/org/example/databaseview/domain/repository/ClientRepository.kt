package org.example.databaseview.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.databaseview.domain.model.*

interface ClientRepository {
    fun getClientsWithAllContracts(): Flow<List<ClientWithContractsModel>>
    suspend fun getClientWithContracts(clientId: Int): Result<ClientWithContractsModel>
    suspend fun updateClient(client: Client): Result<Boolean>
    suspend fun deleteClient(clientId: Int): Result<Boolean>
    suspend fun createClient(client: Client): Result<Boolean>
}