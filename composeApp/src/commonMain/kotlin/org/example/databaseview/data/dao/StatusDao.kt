package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.mapper.toStatusModel
import org.example.databaseview.data.table.StatusTable
import org.example.databaseview.domain.model.Status
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class StatusDao : CrudDao<Status> {

    override fun create(entity: Status): Boolean =
        StatusTable.insert {
            it[statusName] = entity.statusName
        }.insertedCount > 0

    override fun read(id: Int): Status? =
        StatusTable.select { StatusTable.id eq id }
            .map(ResultRow::toStatusModel)
            .singleOrNull()

    override fun readAll(): List<Status> =
        StatusTable.selectAll().map(ResultRow::toStatusModel)

    override fun update(entity: Status): Boolean =
        StatusTable.update({ StatusTable.id eq entity.id }) {
            it[statusName] = entity.statusName
        } > 0

    override fun delete(id: Int): Boolean =
        StatusTable.deleteWhere { this.id eq id } > 0
}
