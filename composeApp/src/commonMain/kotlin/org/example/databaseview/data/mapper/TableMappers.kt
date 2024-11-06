package org.example.databaseview.data.mapper

import org.example.databaseview.data.table.*
import org.example.databaseview.domain.model.*
import org.jetbrains.exposed.sql.ResultRow


fun ResultRow.toClientModel(): Client {
    return Client(
        clientId = this[ClientTable.clientId],
        fullName = this[ClientTable.fullName],
        phone = this[ClientTable.phone]
    )
}

fun ResultRow.toPositionModel(): Position {
    return Position(
        positionId = this[PositionTable.positionId],
        title = this[PositionTable.title],
        salary = this[PositionTable.salary].toDouble()
    )
}

fun ResultRow.toWorkerModel(): Worker {
    return Worker(
        workerId = this[WorkerTable.workerId],
        departmentId = this[WorkerTable.departmentId],
        fullName = this[WorkerTable.fullName],
        phone = this[WorkerTable.phone],
        positionId = this[WorkerTable.positionId]
    )
}

fun ResultRow.toDepartmentModel(): Department {
    return Department(
        departmentId = this[DepartmentTable.departmentId],
        name = this[DepartmentTable.name],
        phone = this[DepartmentTable.phone]
    )
}

fun ResultRow.toLicense1CModel(): License1C {
    return License1C(
        licenseId = this[License1CTable.licenseId],
        workerId = this[License1CTable.workerId],
        version = this[License1CTable.version],
        expirationDate = this[License1CTable.expirationDate]
    )
}

fun ResultRow.toContractModel(): Contract {
    return Contract(
        contractId = this[ContractTable.contractId],
        clientId = this[ContractTable.clientId],
        amount = this[ContractTable.amount].toDouble(),
        deadline = this[ContractTable.deadline]
    )
}

fun ResultRow.toProjectModel(): Project {
    return Project(
        projectId = this[ProjectTable.projectId],
        name = this[ProjectTable.name],
        requirements = this[ProjectTable.requirements],
        contractId = this[ProjectTable.contractId]
    )
}

fun ResultRow.toStatusModel(): Status {
    return Status(
        statusId = this[StatusTable.statusId],
        statusName = this[StatusTable.statusName]
    )
}

fun ResultRow.toTaskModel(): Task {
    return Task(
        taskId = this[TaskTable.taskId],
        projectId = this[TaskTable.projectId],
        name = this[TaskTable.name],
        description = this[TaskTable.description],
        deadlineDate = this[TaskTable.deadlineDate],
        statusId = this[TaskTable.statusId],
        authorId = this[TaskTable.authorId]
    )
}

fun ResultRow.toTaskWorkerModel(): TaskWorker {
    return TaskWorker(
        taskId = this[TaskWorkerTable.taskId],
        workerId = this[TaskWorkerTable.workerId]
    )
}