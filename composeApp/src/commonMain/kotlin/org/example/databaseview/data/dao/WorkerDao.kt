package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.mapper.toWorkerModel
import org.example.databaseview.data.table.WorkerTable
import org.example.databaseview.domain.model.Worker
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class WorkerDao : CrudDao<Worker> {
    override fun create(entity: Worker): Boolean =
        WorkerTable.insert {
            it[departmentId] = entity.departmentId
            it[fullName] = entity.fullName
            it[phone] = entity.phone
            it[positionId] = entity.positionId
        }.insertedCount > 0

    override fun read(id: Int): Worker? =
        WorkerTable.select { WorkerTable.id eq id }
            .map(ResultRow::toWorkerModel)
            .singleOrNull()

    override fun readAll(): List<Worker> =
        WorkerTable.selectAll().map(ResultRow::toWorkerModel)

    override fun update(entity: Worker): Boolean =
        WorkerTable.update({ WorkerTable.id eq entity.id }) {
            it[departmentId] = entity.departmentId
            it[fullName] = entity.fullName
            it[phone] = entity.phone
            it[positionId] = entity.positionId
        } > 0

    override fun delete(id: Int): Boolean =
        WorkerTable.deleteWhere { this.id eq id } > 0
}
