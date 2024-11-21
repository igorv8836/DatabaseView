package org.example.databaseview.domain.model

import androidx.compose.runtime.Immutable
import java.time.LocalDate

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
    val id: Int = -1,
    val departmentId: Int = -1,
    val fullName: String = "",
    val phone: String = "",
    val positionId: Int = -1
)

@Immutable
data class License1C(
    val id: Int,
    val workerId: Int,
    val version: String,
    val expirationDate: LocalDate
)

data class Client(
    val id: Int = -1,
    val fullName: String = "",
    val phone: String = ""
)

@Immutable
data class Contract(
    val id: Int = -1,
    val clientId: Int = -1,
    val amount: Double = 0.0,
    val deadline: LocalDate = LocalDate.now()
)

@Immutable
data class Project(
    val id: Int = -1,
    val name: String = "",
    val requirements: String? = null,
    val contractId: Int = -1
)

data class Status(
    val id: Int = -1,
    val statusName: String = ""
)

@Immutable
data class Task(
    val id: Int = -1,
    val projectId: Int = -1,
    val name: String = "",
    val description: String? = null,
    val deadlineDate: LocalDate = LocalDate.now(),
    val statusId: Int = -1,
    val authorId: Int = -1
)

data class TaskWorker(
    val taskId: Int,
    val workerId: Int
)
