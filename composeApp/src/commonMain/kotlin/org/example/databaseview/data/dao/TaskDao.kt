package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.data.mapper.toTaskModel
import org.example.databaseview.data.table.TaskTable
import org.example.databaseview.domain.model.Task
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class TaskDao : CrudDao<Task> {

    override suspend fun create(entity: Task): Boolean = dbQuery {
        TaskTable.insert {
            it[projectId] = entity.projectId
            it[name] = entity.name
            it[description] = entity.description
            it[deadlineDate] = entity.deadlineDate
            it[statusId] = entity.statusId
            it[authorId] = entity.authorId
        }.insertedCount > 0
    }

    override suspend fun read(id: Int): Task? = dbQuery {
        TaskTable.select { TaskTable.taskId eq id }
            .map(ResultRow::toTaskModel)
            .singleOrNull()
    }

    override suspend fun readAll(): List<Task> = dbQuery {
        TaskTable.selectAll().map(ResultRow::toTaskModel)
    }

    override suspend fun update(entity: Task): Boolean = dbQuery {
        TaskTable.update({ TaskTable.taskId eq entity.taskId }) {
            it[projectId] = entity.projectId
            it[name] = entity.name
            it[description] = entity.description
            it[deadlineDate] = entity.deadlineDate
            it[statusId] = entity.statusId
            it[authorId] = entity.authorId
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        TaskTable.deleteWhere { taskId eq id } > 0
    }
}
