package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.mapper.toDepartmentModel
import org.example.databaseview.data.table.DepartmentTable
import org.example.databaseview.domain.model.Department
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DepartmentDao : CrudDao<Department> {

    override fun create(entity: Department): Boolean =
        DepartmentTable.insert {
            it[name] = entity.name
            it[phone] = entity.phone
        }.insertedCount > 0

    override fun read(id: Int): Department? =
        DepartmentTable.select { DepartmentTable.id eq id }
            .map(ResultRow::toDepartmentModel)
            .singleOrNull()

    override fun readAll(): List<Department> =
        DepartmentTable.selectAll().map(ResultRow::toDepartmentModel)

    override fun update(entity: Department): Boolean =
        DepartmentTable.update({ DepartmentTable.id eq entity.id }) {
            it[name] = entity.name
            it[phone] = entity.phone
        } > 0

    override fun delete(id: Int): Boolean =
        DepartmentTable.deleteWhere { this.id eq id } > 0

}
