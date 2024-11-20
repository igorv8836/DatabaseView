package org.example.databaseview.domain.model

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class ProjectFullModel(
    val project: Project = Project(-1, "", "", -1),
    val tasks: List<ProjectTaskModel> = emptyList(),
    val contract: ContractClientModel = ContractClientModel(
        Contract(-1, -1, 0.0, LocalDate.MIN),
        Client(-1, "", "")
    ),
)