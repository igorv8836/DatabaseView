package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.data.mapper.toProjectModel
import org.example.databaseview.data.table.ProjectTable
import org.example.databaseview.domain.model.Project
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ProjectDao : CrudDao<Project> {

    override suspend fun create(entity: Project): Boolean = dbQuery {
        ProjectTable.insert {
            it[name] = entity.name
            it[requirements] = entity.requirements
            it[contractId] = entity.contractId
        }.insertedCount > 0
    }

    override suspend fun read(id: Int): Project? = dbQuery {
        ProjectTable.select { ProjectTable.projectId eq id }
            .map(ResultRow::toProjectModel)
            .singleOrNull()
    }

    override suspend fun readAll(): List<Project> = dbQuery {
        ProjectTable.selectAll().map(ResultRow::toProjectModel)
    }

    override suspend fun update(entity: Project): Boolean = dbQuery {
        ProjectTable.update({ ProjectTable.projectId eq entity.projectId }) {
            it[name] = entity.name
            it[requirements] = entity.requirements
            it[contractId] = entity.contractId
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        ProjectTable.deleteWhere { projectId eq id } > 0
    }
}
