package org.example.databaseview.data.database

import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction


suspend fun <T> dbQuery(block: Transaction.() -> T): T {
    return withContext(Dispatchers.IO) {
        transaction {
            block()
        }
    }
}