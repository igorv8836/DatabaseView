package org.example.databaseview.domain.model

data class Position(
    val positionId: Int,
    val title: String,
    val salary: Double
)

data class Department(
    val departmentId: Int,
    val name: String,
    val phone: String
)

data class Worker(
    val workerId: Int,
    val departmentId: Int,
    val fullName: String,
    val phone: String,
    val positionId: Int
)

data class License1C(
    val licenseId: Int,
    val workerId: Int,
    val version: String,
    val expirationDate: java.time.LocalDate
)

data class Client(
    val clientId: Int,
    val fullName: String,
    val phone: String
)

data class Contract(
    val contractId: Int,
    val clientId: Int,
    val amount: Double,
    val deadline: java.time.LocalDate
)

data class Project(
    val projectId: Int,
    val name: String,
    val requirements: String?,
    val contractId: Int
)

data class Status(
    val statusId: Int,
    val statusName: String
)

data class Task(
    val taskId: Int,
    val projectId: Int,
    val name: String,
    val description: String?,
    val deadlineDate: java.time.LocalDate,
    val statusId: Int,
    val authorId: Int
)

data class TaskWorker(
    val taskId: Int,
    val workerId: Int
)
