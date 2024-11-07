package org.example.databaseview.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class ProjectFullModel(
    val project: Project,
    val tasks: List<Pair<Task, Status>>,
    val contract: Contract
)