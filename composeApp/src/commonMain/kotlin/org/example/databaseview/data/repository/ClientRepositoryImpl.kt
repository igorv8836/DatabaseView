package org.example.databaseview.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import org.example.databaseview.data.dao.*
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.domain.model.*
import org.example.databaseview.domain.repository.ClientRepository

class ClientRepositoryImpl(
    private val clientDao: ClientDao,
    private val contractDao: ContractDao
): ClientRepository {
    override fun getClientsWithAllContracts(): Flow<List<ClientWithContractsModel>> {
        return flow {
            while (true) {
                val clients = dbQuery {
                    clientDao.readAll().map { client ->
                        ClientWithContractsModel(
                            client = client,
                            contracts = contractDao.readByClientId(client.id)
                        )
                    }
                }
                emit(clients)
                delay(5000)
            }
        }
    }

    override suspend fun getClientWithContracts(clientId: Int): Result<ClientWithContractsModel> {
        return Result.runCatching {
            dbQuery {
                val client =
                    clientDao.read(clientId) ?: throw IllegalArgumentException("Client not found")
                val contracts = contractDao.readByClientId(clientId)
                ClientWithContractsModel(client, contracts)
            }
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