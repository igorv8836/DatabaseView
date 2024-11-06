package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.data.mapper.toStatusModel
import org.example.databaseview.data.table.StatusTable
import org.example.databaseview.domain.model.Status
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class StatusDao : CrudDao<Status> {

    override suspend fun create(entity: Status): Boolean = dbQuery {
        StatusTable.insert {
            it[statusName] = entity.statusName
        }.insertedCount > 0
    }

    override suspend fun read(id: Int): Status? = dbQuery {
        StatusTable.select { StatusTable.statusId eq id }
            .map(ResultRow::toStatusModel)
            .singleOrNull()
    }

    override suspend fun readAll(): List<Status> = dbQuery {
        StatusTable.selectAll().map(ResultRow::toStatusModel)
    }

    override suspend fun update(entity: Status): Boolean = dbQuery {
        StatusTable.update({ StatusTable.statusId eq entity.statusId }) {
            it[statusName] = entity.statusName
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        StatusTable.deleteWhere { statusId eq id } > 0
    }
}
