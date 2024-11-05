package org.example.databaseview

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.sql.*

object Positions : Table("positions") {
    val positionId = integer("position_id").autoIncrement()
    val title = varchar("title", length = 255)
    val salary = decimal("salary", scale = 2, precision = 10)
    override val primaryKey = PrimaryKey(positionId)
}

fun connectToDatabase(): Connection? {
    val url = "jdbc:postgresql://138.124.15.21:5432/software_development"
    val user = "postgres"
    val password = "94849887"

    return try {
        DriverManager.getConnection(url, user, password)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Функция для выборки данных из таблицы Positions
fun fetchPositions(): List<Pair<String, BigDecimal>> {
    val positions = mutableListOf<Pair<String, BigDecimal>>()

    // Подключение к базе данных
    val connection = connectToDatabase()

    // Если соединение успешно, выполняем запрос
    connection?.use { conn ->
        val query = "SELECT title, salary FROM positions"
        val statement = conn.createStatement()

        // Выполнение запроса и обработка результата
        val resultSet: ResultSet = statement.executeQuery(query)
        while (resultSet.next()) {
            val title = resultSet.getString("title")
            val salary = resultSet.getBigDecimal("salary")
            positions.add(title to salary)
        }
    }

    return positions
}

//fun connectToDatabase() {
//    Database.connect(
//        url = "jdbc:postgresql://138.124.15.21:5432/software_development",
//        driver = "org.postgresql.Driver",
//        user = "postgres",
//        password = "94849887"
//    )
//}