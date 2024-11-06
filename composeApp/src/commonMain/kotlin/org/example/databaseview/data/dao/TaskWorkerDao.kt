package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CompositeCrudDao
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.data.mapper.toTaskWorkerModel
import org.example.databaseview.data.table.TaskWorkerTable
import org.example.databaseview.domain.model.TaskWorker
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class TaskWorkerDao : CompositeCrudDao<TaskWorker, Int, Int> {

    override suspend fun create(entity: TaskWorker): Boolean = dbQuery {
        TaskWorkerTable.insert {
            it[taskId] = entity.taskId
            it[workerId] = entity.workerId
        }.insertedCount > 0
    }

    override suspend fun readByCompositeKey(key1: Int, key2: Int): TaskWorker? = dbQuery {
        TaskWorkerTable.select { (TaskWorkerTable.taskId eq key1) and (TaskWorkerTable.workerId eq key2) }
            .map(ResultRow::toTaskWorkerModel).singleOrNull()
    }

    override suspend fun readAll(): List<TaskWorker> = dbQuery {
        TaskWorkerTable.selectAll().map(ResultRow::toTaskWorkerModel)
    }

    override suspend fun updateByCompositeKey(entity: TaskWorker, key1: Int, key2: Int): Boolean =
        dbQuery {
            TaskWorkerTable.update({ (TaskWorkerTable.taskId eq key1) and (TaskWorkerTable.workerId eq key2) }) {
                it[taskId] = entity.taskId
                it[workerId] = entity.workerId
            } > 0
        }

    override suspend fun deleteByCompositeKey(key1: Int, key2: Int): Boolean = dbQuery {
        TaskWorkerTable.deleteWhere { (taskId eq key1) and (workerId eq key2) } > 0
    }
}

