package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.data.mapper.toWorkerModel
import org.example.databaseview.data.table.WorkerTable
import org.example.databaseview.domain.model.Worker
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class WorkerDao : CrudDao<Worker> {

    override suspend fun create(entity: Worker): Boolean = dbQuery {
        WorkerTable.insert {
            it[departmentId] = entity.departmentId
            it[fullName] = entity.fullName
            it[phone] = entity.phone
            it[positionId] = entity.positionId
        }.insertedCount > 0
    }

    override suspend fun read(id: Int): Worker? = dbQuery {
        WorkerTable.select { WorkerTable.workerId eq id }
            .map(ResultRow::toWorkerModel)
            .singleOrNull()
    }

    override suspend fun readAll(): List<Worker> = dbQuery {
        WorkerTable.selectAll().map(ResultRow::toWorkerModel)
    }

    override suspend fun update(entity: Worker): Boolean = dbQuery {
        WorkerTable.update({ WorkerTable.workerId eq entity.workerId }) {
            it[departmentId] = entity.departmentId
            it[fullName] = entity.fullName
            it[phone] = entity.phone
            it[positionId] = entity.positionId
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        WorkerTable.deleteWhere { workerId eq id } > 0
    }
}
