package org.example.databaseview.data.dao

import org.example.databaseview.data.dao.interfaces.CrudDao
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.data.mapper.toPositionModel
import org.example.databaseview.data.table.PositionTable
import org.example.databaseview.domain.model.Position
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PositionDao : CrudDao<Position> {

    override suspend fun create(entity: Position): Boolean = dbQuery {
        PositionTable.insert {
            it[title] = entity.title
            it[salary] = entity.salary.toBigDecimal()
        }.insertedCount > 0
    }

    override suspend fun read(id: Int): Position? = dbQuery {
        PositionTable.select { PositionTable.positionId eq id }
            .map(ResultRow::toPositionModel)
            .singleOrNull()
    }

    override suspend fun readAll(): List<Position> = dbQuery {
        PositionTable.selectAll().map(ResultRow::toPositionModel)
    }

    override suspend fun update(entity: Position): Boolean = dbQuery {
        PositionTable.update({ PositionTable.positionId eq entity.positionId }) {
            it[title] = entity.title
            it[salary] = entity.salary.toBigDecimal()
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        PositionTable.deleteWhere { positionId eq id } > 0
    }
}
