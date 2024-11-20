package org.example.databaseview.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.databaseview.domain.model.*

interface ContractRepository {
    fun getContracts(): Flow<List<Pair<Contract, Client>>>
    suspend fun updateContract(project: Contract): Result<Boolean>
    suspend fun deleteContract(contractId: Int): Result<Boolean>
    suspend fun createContract(project: Contract): Result<Boolean>
}