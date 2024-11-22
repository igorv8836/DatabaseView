package org.example.databaseview.presentation.shared_elements

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(message: String) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "Ошибка: $message",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleMedium
        )
    }
}