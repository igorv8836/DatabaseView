package org.example.databaseview.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.databaseview.domain.model.*

interface ClientRepository {
    fun getClients(): Flow<List<Client>>
    suspend fun updateClient(client: Client): Result<Boolean>
    suspend fun deleteClient(clientId: Int): Result<Boolean>
    suspend fun createClient(client: Client): Result<Boolean>
}