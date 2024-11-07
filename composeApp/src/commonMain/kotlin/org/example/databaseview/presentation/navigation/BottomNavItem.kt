package org.example.databaseview.presentation.navigation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class BottomNavItem<T : Any>(
    val title: String,
    val route: T,
    val icon: ImageVector
)