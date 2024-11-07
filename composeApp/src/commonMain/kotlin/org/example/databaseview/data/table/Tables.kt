package org.example.databaseview.data.table

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date

// projects -> tasks -> statuses
//
//
//
//


object PositionTable : Table("positions") {
    val id = integer("position_id").autoIncrement()
    val title = varchar("title", length = 255)
    val salary = decimal("salary", scale = 2, precision = 10)
    override val primaryKey = PrimaryKey(id)
}

object DepartmentTable : Table("departments") {
    val id = integer("department_id").autoIncrement()
    val name = varchar("name", length = 255)
    val phone = varchar("phone", length = 50)
    override val primaryKey = PrimaryKey(id)
}

object WorkerTable : Table("workers") {
    val id = integer("worker_id").autoIncrement()
    val departmentId = integer("department_id").references(
        DepartmentTable.id,
        onDelete = ReferenceOption.RESTRICT
    )
    val fullName = varchar("full_name", length = 255)
    val phone = varchar("phone", length = 50)
    val positionId =
        integer("position_id").references(PositionTable.id, onDelete = ReferenceOption.RESTRICT)
    override val primaryKey = PrimaryKey(id)
}

object License1CTable : Table("licenses_1c") {
    val id = integer("license_id").autoIncrement()
    val workerId =
        integer("worker_id").references(WorkerTable.id, onDelete = ReferenceOption.CASCADE)
    val version = varchar("version", length = 50)
    val expirationDate = date("expiration_date")
    override val primaryKey = PrimaryKey(id)
}

object ClientTable : Table("clients") {
    val id = integer("client_id").autoIncrement()
    val fullName = varchar("full_name", length = 255)
    val phone = varchar("phone", length = 50)
    override val primaryKey = PrimaryKey(id)
}

object ContractTable : Table("contracts") {
    val id = integer("contract_id").autoIncrement()
    val clientId =
        integer("client_id").references(ClientTable.id, onDelete = ReferenceOption.CASCADE)
    val amount = decimal("amount", scale = 2, precision = 10)
    val deadline = date("deadline")
    override val primaryKey = PrimaryKey(id)
}

object ProjectTable : Table("projects") {
    val id = integer("project_id").autoIncrement()
    val name = varchar("name", length = 255)
    val requirements = text("requirements").nullable()
    val contractId = integer("contract_id").references(
        ContractTable.id,
        onDelete = ReferenceOption.CASCADE
    )
    override val primaryKey = PrimaryKey(id)
}

object StatusTable : Table("statuses") {
    val id = integer("status_id").autoIncrement()
    val statusName = varchar("status_name", length = 50)
    override val primaryKey = PrimaryKey(id)
}

object TaskTable : Table("tasks") {
    val id = integer("task_id").autoIncrement()
    val projectId =
        integer("project_id").references(ProjectTable.id, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", length = 255)
    val description = text("description").nullable()
    val deadlineDate = date("deadline_date")
    val statusId =
        integer("status_id").references(StatusTable.id, onDelete = ReferenceOption.RESTRICT)
    val authorId =
        integer("author_id").references(WorkerTable.id, onDelete = ReferenceOption.RESTRICT)
    override val primaryKey = PrimaryKey(id)
}

object TaskWorkerTable : Table("task_workers") {
    val taskId = integer("task_id").references(TaskTable.id, onDelete = ReferenceOption.CASCADE)
    val workerId =
        integer("worker_id").references(WorkerTable.id, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(taskId, workerId)
}
