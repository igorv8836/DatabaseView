package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.mapper.toProjectModel
import org.example.databaseview.data.table.ProjectTable
import org.example.databaseview.domain.model.Project
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ProjectDao : CrudDao<Project> {
    override fun create(entity: Project): Boolean =
        ProjectTable.insert {
            it[name] = entity.name
            it[requirements] = entity.requirements
            it[contractId] = entity.contractId
        }.insertedCount > 0

    override fun read(id: Int): Project? =
        ProjectTable.select { ProjectTable.id eq id }
            .map(ResultRow::toProjectModel)
            .singleOrNull()

    override fun readAll(): List<Project> =
        ProjectTable.selectAll().map(ResultRow::toProjectModel)

    override fun update(entity: Project): Boolean =
        ProjectTable.update({ ProjectTable.id eq entity.id }) {
            it[name] = entity.name
            it[requirements] = entity.requirements
            it[contractId] = entity.contractId
        } > 0

    override fun delete(id: Int): Boolean =
        ProjectTable.deleteWhere { this.id eq id } > 0
}
