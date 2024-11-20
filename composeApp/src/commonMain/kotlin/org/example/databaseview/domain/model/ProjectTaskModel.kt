package org.example.databaseview.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class ProjectTaskModel(
    val task: Task,
    val status: Status,
    val author: Worker,
)