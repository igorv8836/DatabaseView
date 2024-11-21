package org.example.databaseview.presentation.projects_screen.projects_details_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.databaseview.domain.model.ProjectTaskModel

@Composable
fun TaskItem(element: ProjectTaskModel, onClick: () -> Unit) {
    Card(
        onClick = onClick, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Задача: ${element.task.name}", style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Статус: ${element.status.statusName}",
                style = MaterialTheme.typography.bodyMedium
            )
            if (element.task.description != null) {
                Text(
                    text = "Описание: ${element.task.description}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = "Дедлайн: ${element.task.deadlineDate}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Автор: ${element.author.fullName}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}