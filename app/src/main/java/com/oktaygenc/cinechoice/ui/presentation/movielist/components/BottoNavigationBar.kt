package com.oktaygenc.cinechoice.ui.presentation.movielist.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.oktaygenc.cinechoice.ui.theme.TopAndBottomBarColor

@Composable
fun BottomNavigationBar(navController: NavController) {
    // List of bottom navigation items with labels, icons, and routes
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Explore", Icons.Default.Search, "explore"),
        BottomNavItem("Favorite", Icons.Default.Favorite, "favorite"),
        BottomNavItem("Profile", Icons.Default.Person, "profile")
    )

    // NavigationBar container with a custom color
    NavigationBar(containerColor = TopAndBottomBarColor) {
        // Get the current route from the nav controller's back stack
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        // Create a navigation item for each entry
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route, // Highlight selected item
                onClick = {
                    // Navigate if the current route is not the same as the item route
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Preserve state on navigation
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true // Avoid multiple instances of the same screen
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent // No indicator color
                )
            )
        }
    }
}

// Data class for bottom navigation items
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)
