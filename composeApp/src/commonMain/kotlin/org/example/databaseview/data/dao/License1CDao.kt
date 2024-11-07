package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.mapper.toLicense1CModel
import org.example.databaseview.data.table.License1CTable
import org.example.databaseview.domain.model.License1C
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class License1CDao : CrudDao<License1C> {
    override fun create(entity: License1C): Boolean =
        License1CTable.insert {
            it[workerId] = entity.workerId
            it[version] = entity.version
            it[expirationDate] = entity.expirationDate
        }.insertedCount > 0

    override fun read(id: Int): License1C? =
        License1CTable.select { License1CTable.id eq id }
            .map(ResultRow::toLicense1CModel)
            .singleOrNull()

    override fun readAll(): List<License1C> =
        License1CTable.selectAll().map(ResultRow::toLicense1CModel)

    override fun update(entity: License1C): Boolean =
        License1CTable.update({ License1CTable.id eq entity.id }) {
            it[workerId] = entity.workerId
            it[version] = entity.version
            it[expirationDate] = entity.expirationDate
        } > 0

    override fun delete(id: Int): Boolean =
        License1CTable.deleteWhere { this.id eq id } > 0

}
