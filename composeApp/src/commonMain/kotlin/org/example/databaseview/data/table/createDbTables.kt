package org.example.databaseview.data.table

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun createDbTables() {
    transaction {
        SchemaUtils.create(
            PositionTable,
            DepartmentTable,
            WorkerTable,
            License1CTable,
            ClientTable,
            ContractTable,
            ProjectTable,
            StatusTable,
            TaskTable,
            TaskWorkerTable
        )
    }
}