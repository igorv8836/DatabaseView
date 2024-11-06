package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.data.mapper.toContractModel
import org.example.databaseview.data.table.ContractTable
import org.example.databaseview.domain.model.Contract
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ContractDao : CrudDao<Contract> {

    override suspend fun create(entity: Contract): Boolean = dbQuery {
        ContractTable.insert {
            it[clientId] = entity.clientId
            it[amount] = entity.amount.toBigDecimal()
            it[deadline] = entity.deadline
        }.insertedCount > 0
    }

    override suspend fun read(id: Int): Contract? = dbQuery {
        ContractTable.select { ContractTable.contractId eq id }
            .map(ResultRow::toContractModel)
            .singleOrNull()
    }

    override suspend fun readAll(): List<Contract> = dbQuery {
        ContractTable.selectAll().map(ResultRow::toContractModel)
    }

    override suspend fun update(entity: Contract): Boolean = dbQuery {
        ContractTable.update({ ContractTable.contractId eq entity.contractId }) {
            it[clientId] = entity.clientId
            it[amount] = entity.amount.toBigDecimal()
            it[deadline] = entity.deadline
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        ContractTable.deleteWhere { contractId eq id } > 0
    }
}
