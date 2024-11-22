package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.mapper.toContractModel
import org.example.databaseview.data.table.ContractTable
import org.example.databaseview.domain.model.Contract
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ContractDao : CrudDao<Contract> {

    override fun create(entity: Contract): Boolean =
        ContractTable.insert {
            it[clientId] = entity.clientId
            it[amount] = entity.amount.toBigDecimal()
            it[deadline] = entity.deadline
        }.insertedCount > 0

    override fun read(id: Int): Contract? =
        ContractTable.select { ContractTable.id eq id }
            .map(ResultRow::toContractModel)
            .singleOrNull()

    override fun readAll(): List<Contract> =
        ContractTable.selectAll().map(ResultRow::toContractModel)

    fun readByClientId(clientId: Int) =
        ContractTable.select { ContractTable.clientId eq clientId }
            .map(ResultRow::toContractModel)

    override fun update(entity: Contract): Boolean =
        ContractTable.update({ ContractTable.id eq entity.id }) {
            it[clientId] = entity.clientId
            it[amount] = entity.amount.toBigDecimal()
            it[deadline] = entity.deadline
        } > 0

    override fun delete(id: Int): Boolean =
        ContractTable.deleteWhere { this.id eq id } > 0

}
