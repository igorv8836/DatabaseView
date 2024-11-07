package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.mapper.toTaskModel
import org.example.databaseview.data.table.TaskTable
import org.example.databaseview.domain.model.Task
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class TaskDao : CrudDao<Task> {

    override fun create(entity: Task): Boolean =
        TaskTable.insert {
            it[projectId] = entity.projectId
            it[name] = entity.name
            it[description] = entity.description
            it[deadlineDate] = entity.deadlineDate
            it[statusId] = entity.statusId
            it[authorId] = entity.authorId
        }.insertedCount > 0

    override fun read(id: Int): Task? =
        TaskTable.select { TaskTable.id eq id }
            .map(ResultRow::toTaskModel)
            .singleOrNull()

    override fun readAll(): List<Task> =
        TaskTable.selectAll().map(ResultRow::toTaskModel)

    override fun update(entity: Task): Boolean =
        TaskTable.update({ TaskTable.id eq entity.id }) {
            it[projectId] = entity.projectId
            it[name] = entity.name
            it[description] = entity.description
            it[deadlineDate] = entity.deadlineDate
            it[statusId] = entity.statusId
            it[authorId] = entity.authorId
        } > 0

    override fun delete(id: Int): Boolean =
        TaskTable.deleteWhere { this.id eq id } > 0

    fun readByProjectId(projectId: Int): List<Task> =
        TaskTable.select { TaskTable.projectId eq projectId }
            .map(ResultRow::toTaskModel)
}
