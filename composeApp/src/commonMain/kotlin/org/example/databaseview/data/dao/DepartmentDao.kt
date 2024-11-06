package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.data.mapper.toDepartmentModel
import org.example.databaseview.data.table.DepartmentTable
import org.example.databaseview.domain.model.Department
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DepartmentDao : CrudDao<Department> {

    override suspend fun create(entity: Department): Boolean = dbQuery {
        DepartmentTable.insert {
            it[name] = entity.name
            it[phone] = entity.phone
        }.insertedCount > 0
    }

    override suspend fun read(id: Int): Department? = dbQuery {
        DepartmentTable.select { DepartmentTable.departmentId eq id }
            .map(ResultRow::toDepartmentModel)
            .singleOrNull()
    }

    override suspend fun readAll(): List<Department> = dbQuery {
        DepartmentTable.selectAll().map(ResultRow::toDepartmentModel)
    }

    override suspend fun update(entity: Department): Boolean = dbQuery {
        DepartmentTable.update({ DepartmentTable.departmentId eq entity.departmentId }) {
            it[name] = entity.name
            it[phone] = entity.phone
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        DepartmentTable.deleteWhere { departmentId eq id } > 0
    }
}
