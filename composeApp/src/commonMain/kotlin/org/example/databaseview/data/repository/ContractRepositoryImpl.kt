package org.example.databaseview.data.repository

import kotlinx.coroutines.flow.*
import org.example.databaseview.data.dao.*
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.domain.model.*
import org.example.databaseview.domain.repository.ContractRepository

class ContractRepositoryImpl(
    private val contractDao: ContractDao,
    private val clientDao: ClientDao
): ContractRepository {
    override fun getContracts(): Flow<List<Pair<Contract, Client>>> {
        return flow {
            val contractsWithClient = dbQuery {
                contractDao.readAll().map { contract ->
                    val client = clientDao.read(contract.clientId)
                    client?.let { Pair(contract, client) } ?: throw Exception("Client not found")
                }
            }
            emit(contractsWithClient)
        }
    }

    override suspend fun updateContract(project: Contract): Result<Boolean> {
        return Result.runCatching { dbQuery { contractDao.update(project) } }
    }

    override suspend fun deleteContract(contractId: Int): Result<Boolean> {
        return Result.runCatching { dbQuery { contractDao.delete(contractId) } }
    }

    override suspend fun createContract(project: Contract): Result<Boolean> {
        return Result.runCatching { dbQuery { contractDao.create(project) } }
    }

}