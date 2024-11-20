package org.example.databaseview.domain.model

import androidx.compose.runtime.*

data class Position(
    val id: Int,
    val title: String,
    val salary: Double
)

data class Department(
    val id: Int,
    val name: String,
    val phone: String
)

data class Worker(
    val id: Int,
    val departmentId: Int,
    val fullName: String,
    val phone: String,
    val positionId: Int
)

@Immutable
data class License1C(
    val id: Int,
    val workerId: Int,
    val version: String,
    val expirationDate: java.time.LocalDate
)

data class Client(
    val id: Int,
    val fullName: String,
    val phone: String
)

@Immutable
data class Contract(
    val id: Int,
    val clientId: Int,
    val amount: Double,
    val deadline: java.time.LocalDate
)

@Immutable
data class Project(
    val id: Int,
    val name: String,
    val requirements: String?,
    val contractId: Int
)

data class Status(
    val id: Int,
    val statusName: String
)

@Immutable
data class Task(
    val id: Int,
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
