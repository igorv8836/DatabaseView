package org.example.databaseview.data.table

import org.example.databaseview.Positions
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date

object PositionTable : Table("positions") {
    val positionId = integer("position_id").autoIncrement()
    val title = varchar("title", length = 255)
    val salary = decimal("salary", scale = 2, precision = 10)
    override val primaryKey = PrimaryKey(positionId)
}

object DepartmentTable : Table("departments") {
    val departmentId = integer("department_id").autoIncrement()
    val name = varchar("name", length = 255)
    val phone = varchar("phone", length = 50)
    override val primaryKey = PrimaryKey(departmentId)
}

object WorkerTable : Table("workers") {
    val workerId = integer("worker_id").autoIncrement()
    val departmentId = integer("department_id").references(
        DepartmentTable.departmentId,
        onDelete = ReferenceOption.RESTRICT
    )
    val fullName = varchar("full_name", length = 255)
    val phone = varchar("phone", length = 50)
    val positionId =
        integer("position_id").references(Positions.positionId, onDelete = ReferenceOption.RESTRICT)
    override val primaryKey = PrimaryKey(workerId)
}

object License1CTable : Table("licenses_1c") {
    val licenseId = integer("license_id").autoIncrement()
    val workerId =
        integer("worker_id").references(WorkerTable.workerId, onDelete = ReferenceOption.CASCADE)
    val version = varchar("version", length = 50)
    val expirationDate = date("expiration_date")
    override val primaryKey = PrimaryKey(licenseId)
}

object ClientTable : Table("clients") {
    val clientId = integer("client_id").autoIncrement()
    val fullName = varchar("full_name", length = 255)
    val phone = varchar("phone", length = 50)
    override val primaryKey = PrimaryKey(clientId)
}

object ContractTable : Table("contracts") {
    val contractId = integer("contract_id").autoIncrement()
    val clientId =
        integer("client_id").references(ClientTable.clientId, onDelete = ReferenceOption.CASCADE)
    val amount = decimal("amount", scale = 2, precision = 10)
    val deadline = date("deadline")
    override val primaryKey = PrimaryKey(contractId)
}

object ProjectTable : Table("projects") {
    val projectId = integer("project_id").autoIncrement()
    val name = varchar("name", length = 255)
    val requirements = text("requirements").nullable()
    val contractId = integer("contract_id").references(
        ContractTable.contractId,
        onDelete = ReferenceOption.CASCADE
    )
    override val primaryKey = PrimaryKey(projectId)
}

object StatusTable : Table("statuses") {
    val statusId = integer("status_id").autoIncrement()
    val statusName = varchar("status_name", length = 50)
    override val primaryKey = PrimaryKey(statusId)
}

object TaskTable : Table("tasks") {
    val taskId = integer("task_id").autoIncrement()
    val projectId =
        integer("project_id").references(ProjectTable.projectId, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", length = 255)
    val description = text("description").nullable()
    val deadlineDate = date("deadline_date")
    val statusId =
        integer("status_id").references(StatusTable.statusId, onDelete = ReferenceOption.RESTRICT)
    val authorId =
        integer("author_id").references(WorkerTable.workerId, onDelete = ReferenceOption.RESTRICT)
    override val primaryKey = PrimaryKey(taskId)
}

object TaskWorkerTable : Table("task_workers") {
    val taskId = integer("task_id").references(TaskTable.taskId, onDelete = ReferenceOption.CASCADE)
    val workerId =
        integer("worker_id").references(WorkerTable.workerId, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(taskId, workerId)
}
