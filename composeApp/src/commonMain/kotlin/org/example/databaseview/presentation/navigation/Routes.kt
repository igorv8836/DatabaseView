package org.example.databaseview.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object ProjectsRoute

@Serializable
data object WorkersRoute

@Serializable
data object ClientsRoute

@Serializable
data class ProjectDetailsRoute(val id: Int?)

@Serializable
data class TaskDetailsRoute(val id: Int?)