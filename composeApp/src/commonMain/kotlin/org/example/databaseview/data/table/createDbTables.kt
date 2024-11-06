package org.example.databaseview.data.table

import org.jetbrains.exposed.sql.SchemaUtils

fun createDbTables() {
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