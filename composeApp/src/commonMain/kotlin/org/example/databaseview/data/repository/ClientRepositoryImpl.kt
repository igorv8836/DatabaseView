package org.example.databaseview.data.repository

import kotlinx.coroutines.flow.*
import org.example.databaseview.data.dao.ClientDao
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.domain.model.*
import org.example.databaseview.domain.repository.ClientRepository

class ClientRepositoryImpl(
    private val clientDao: ClientDao
): ClientRepository {
    override fun getClients(): Flow<List<Client>> {
        return flow {
            val clients = dbQuery { clientDao.readAll() }
            emit(clients)
        }
    }

    override suspend fun updateClient(client: Client): Result<Boolean> {
        return Result.runCatching { dbQuery { clientDao.update(client) } }
    }

    override suspend fun deleteClient(clientId: Int): Result<Boolean> {
        return Result.runCatching { dbQuery { clientDao.delete(clientId) } }
    }

    override suspend fun createClient(client: Client): Result<Boolean> {
        return Result.runCatching { dbQuery { clientDao.create(client) } }
    }
}