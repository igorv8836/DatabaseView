package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.data.mapper.toClientModel
import org.example.databaseview.data.table.ClientTable
import org.example.databaseview.domain.model.Client
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ClientDao : CrudDao<Client> {

    override suspend fun create(entity: Client): Boolean = dbQuery {
        ClientTable.insert {
            it[fullName] = entity.fullName
            it[phone] = entity.phone
        }.insertedCount > 0
    }

    override suspend fun read(id: Int): Client? = dbQuery {
        ClientTable.select { ClientTable.clientId eq id }
            .map(ResultRow::toClientModel)
            .singleOrNull()
    }

    override suspend fun readAll(): List<Client> = dbQuery {
        ClientTable.selectAll().map(ResultRow::toClientModel)
    }

    override suspend fun update(entity: Client): Boolean = dbQuery {
        ClientTable.update({ ClientTable.clientId eq entity.clientId }) {
            it[fullName] = entity.fullName
            it[phone] = entity.phone
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        ClientTable.deleteWhere { clientId eq id } > 0
    }
}
