package org.example.databaseview.presentation.projects_screen.projects_details_screen

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun DeleteConfirmationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = onConfirm,
                    colors = ButtonDefaults.textButtonColors()
                        .copy(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Отмена")
                }
            },
            title = { Text("Подтверждение удаления") },
            text = { Text("Вы уверены, что хотите удалить?") }
        )
    }
}