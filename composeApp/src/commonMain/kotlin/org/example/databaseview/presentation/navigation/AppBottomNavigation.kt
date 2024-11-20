package org.example.databaseview.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

@Composable
fun AppBottomNavigation(
    navController: NavHostController
) {
    val items = remember {
        listOf(
            BottomNavItem(
                "Проекты", ProjectsRoute, Icons.Filled.Work
            ), BottomNavItem(
                "Сотрудники", WorkersRoute, Icons.Filled.People
            ), BottomNavItem(
                "Клиенты", ClientsRoute, Icons.Filled.Person
            )
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val visible by remember(currentDestination) {
        derivedStateOf {
            items.any { el -> currentDestination?.hierarchy?.any { it.hasRoute(el.route::class) } == true }
        }
    }

    Column {
        if (visible) {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                items.forEach { topLevelRoute ->
                    val isSelected =
                        currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true
                    NavigationBarItem(icon = {
                        Icon(
                            topLevelRoute.icon,
                            contentDescription = topLevelRoute.title,
                            tint = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }, label = {
                        Text(
                            topLevelRoute.title,
                            color = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }, colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ), selected = isSelected, onClick = {
                        navController.navigate(topLevelRoute.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
                }
            }
        }
    }
}