package org.example.databaseview.presentation.clients_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.databaseview.domain.model.Contract

@Composable
fun ContractItem(element: Contract, onClick: () -> Unit) {
    Card(
        onClick = onClick, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "ID: ${element.id}", style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Сумма проекта: ${element.amount}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Дедлайн: ${element.deadline}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}