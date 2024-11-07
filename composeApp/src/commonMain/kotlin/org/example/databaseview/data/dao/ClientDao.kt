package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.mapper.toClientModel
import org.example.databaseview.data.table.ClientTable
import org.example.databaseview.domain.model.Client
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ClientDao : CrudDao<Client> {

    override fun create(entity: Client): Boolean {
        return ClientTable.insert {
            it[fullName] = entity.fullName
            it[phone] = entity.phone
        }.insertedCount > 0
    }

    override fun read(id: Int): Client? {
        return ClientTable.select { ClientTable.id eq id }
            .map(ResultRow::toClientModel)
            .singleOrNull()
    }

    override fun readAll(): List<Client> {
        return ClientTable.selectAll().map(ResultRow::toClientModel)
    }

    override fun update(entity: Client): Boolean {
        return ClientTable.update({ ClientTable.id eq entity.id }) {
            it[fullName] = entity.fullName
            it[phone] = entity.phone
        } > 0
    }

    override fun delete(id: Int): Boolean {
        return ClientTable.deleteWhere { this.id eq id } > 0
    }
}
