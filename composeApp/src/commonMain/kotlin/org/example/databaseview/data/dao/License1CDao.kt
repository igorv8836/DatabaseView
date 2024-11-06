package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.data.mapper.toLicense1CModel
import org.example.databaseview.data.table.License1CTable
import org.example.databaseview.domain.model.License1C
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class License1CDao : CrudDao<License1C> {

    override suspend fun create(entity: License1C): Boolean = dbQuery {
        License1CTable.insert {
            it[workerId] = entity.workerId
            it[version] = entity.version
            it[expirationDate] = entity.expirationDate
        }.insertedCount > 0
    }

    override suspend fun read(id: Int): License1C? = dbQuery {
        License1CTable.select { License1CTable.licenseId eq id }
            .map(ResultRow::toLicense1CModel)
            .singleOrNull()
    }

    override suspend fun readAll(): List<License1C> = dbQuery {
        License1CTable.selectAll().map(ResultRow::toLicense1CModel)
    }

    override suspend fun update(entity: License1C): Boolean = dbQuery {
        License1CTable.update({ License1CTable.licenseId eq entity.licenseId }) {
            it[workerId] = entity.workerId
            it[version] = entity.version
            it[expirationDate] = entity.expirationDate
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        License1CTable.deleteWhere { licenseId eq id } > 0
    }
}
