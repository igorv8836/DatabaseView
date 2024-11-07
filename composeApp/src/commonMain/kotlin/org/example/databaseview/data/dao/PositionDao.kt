package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.mapper.toPositionModel
import org.example.databaseview.data.table.PositionTable
import org.example.databaseview.domain.model.Position
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PositionDao : CrudDao<Position> {

    override fun create(entity: Position): Boolean =
        PositionTable.insert {
            it[title] = entity.title
            it[salary] = entity.salary.toBigDecimal()
        }.insertedCount > 0

    override fun read(id: Int): Position? =
        PositionTable.select { PositionTable.id eq id }
            .map(ResultRow::toPositionModel)
            .singleOrNull()

    override fun readAll(): List<Position> =
        PositionTable.selectAll().map(ResultRow::toPositionModel)

    override fun update(entity: Position): Boolean =
        PositionTable.update({ PositionTable.id eq entity.id }) {
            it[title] = entity.title
            it[salary] = entity.salary.toBigDecimal()
        } > 0

    override fun delete(id: Int): Boolean =
        PositionTable.deleteWhere { this.id eq id } > 0
}
