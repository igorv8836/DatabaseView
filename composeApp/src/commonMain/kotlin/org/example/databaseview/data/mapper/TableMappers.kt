package org.example.databaseview.data.mapper

import org.example.databaseview.data.table.*
import org.example.databaseview.domain.model.*
import org.jetbrains.exposed.sql.ResultRow


fun ResultRow.toClientModel(): Client {
    return Client(
        id = this[ClientTable.id],
        fullName = this[ClientTable.fullName],
        phone = this[ClientTable.phone]
    )
}

fun ResultRow.toPositionModel(): Position {
    return Position(
        id = this[PositionTable.id],
        title = this[PositionTable.title],
        salary = this[PositionTable.salary].toDouble()
    )
}

fun ResultRow.toWorkerModel(): Worker {
    return Worker(
        id = this[WorkerTable.id],
        departmentId = this[WorkerTable.departmentId],
        fullName = this[WorkerTable.fullName],
        phone = this[WorkerTable.phone],
        positionId = this[WorkerTable.positionId]
    )
}

fun ResultRow.toDepartmentModel(): Department {
    return Department(
        id = this[DepartmentTable.id],
        name = this[DepartmentTable.name],
        phone = this[DepartmentTable.phone]
    )
}

fun ResultRow.toLicense1CModel(): License1C {
    return License1C(
        id = this[License1CTable.id],
        workerId = this[License1CTable.workerId],
        version = this[License1CTable.version],
        expirationDate = this[License1CTable.expirationDate]
    )
}

fun ResultRow.toContractModel(): Contract {
    return Contract(
        id = this[ContractTable.id],
        clientId = this[ContractTable.clientId],
        amount = this[ContractTable.amount].toDouble(),
        deadline = this[ContractTable.deadline]
    )
}

fun ResultRow.toProjectModel(): Project {
    return Project(
        id = this[ProjectTable.id],
        name = this[ProjectTable.name],
        requirements = this[ProjectTable.requirements],
        contractId = this[ProjectTable.contractId]
    )
}

fun ResultRow.toStatusModel(): Status {
    return Status(
        id = this[StatusTable.id],
        statusName = this[StatusTable.statusName]
    )
}

fun ResultRow.toTaskModel(): Task {
    return Task(
        id = this[TaskTable.id],
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