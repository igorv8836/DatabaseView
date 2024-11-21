package org.example.databaseview.presentation.shared_elements

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.*

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}