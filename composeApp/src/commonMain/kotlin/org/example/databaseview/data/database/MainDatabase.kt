package org.example.databaseview.data.database

import com.typesafe.config.ConfigFactory
import org.example.databaseview.data.table.createDbTables
import org.jetbrains.exposed.sql.Database

class MainDatabase {

    private val config = ConfigFactory.load()

    fun connect() {
        val dbConfig = config.getConfig("database")
        Database.connect(
            url = dbConfig.getString("url"),
            driver = dbConfig.getString("driver"),
            user = dbConfig.getString("user"),
            password = dbConfig.getString("password")
        )
        createDbTables()
    }
}