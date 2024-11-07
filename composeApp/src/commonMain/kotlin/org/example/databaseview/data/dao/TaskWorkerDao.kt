package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CompositeCrudDao
import org.example.databaseview.data.mapper.toTaskWorkerModel
import org.example.databaseview.data.table.TaskWorkerTable
import org.example.databaseview.domain.model.TaskWorker
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class TaskWorkerDao : CompositeCrudDao<TaskWorker, Int, Int> {

    override fun create(entity: TaskWorker): Boolean =
        TaskWorkerTable.insert {
            it[taskId] = entity.taskId
            it[workerId] = entity.workerId
        }.insertedCount > 0

    override fun readByCompositeKey(key1: Int, key2: Int): TaskWorker? =
        TaskWorkerTable.select { (TaskWorkerTable.taskId eq key1) and (TaskWorkerTable.workerId eq key2) }
            .map(ResultRow::toTaskWorkerModel).singleOrNull()

    override fun readAll(): List<TaskWorker> =
        TaskWorkerTable.selectAll().map(ResultRow::toTaskWorkerModel)

    override fun updateByCompositeKey(entity: TaskWorker, key1: Int, key2: Int): Boolean =
            TaskWorkerTable.update({ (TaskWorkerTable.taskId eq key1) and (TaskWorkerTable.workerId eq key2) }) {
                it[taskId] = entity.taskId
                it[workerId] = entity.workerId
            } > 0

    override fun deleteByCompositeKey(key1: Int, key2: Int): Boolean =
        TaskWorkerTable.deleteWhere { (taskId eq key1) and (workerId eq key2) } > 0
}

