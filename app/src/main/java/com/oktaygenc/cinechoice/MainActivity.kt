package com.oktaygenc.cinechoice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oktaygenc.cinechoice.presentation.navigation.navgraph.Navigation
import com.oktaygenc.cinechoice.presentation.navigation.screens.NavigationScreens
import com.oktaygenc.cinechoice.presentation.screens.movielist.components.BottomNavigationBar
import com.oktaygenc.cinechoice.presentation.common.theme.CineChoiceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // This annotation is used to indicate that the MainActivity will inject dependencies with Hilt.
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            CineChoiceTheme {
                Scaffold(content = {
                    Navigation(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        navController = navController,
                        startDestination = "splash" // Define the initial screen as "splash"
                    )
                },
                    // Bottom bar is conditionally displayed based on the current route
                    bottomBar = {
                        if (NavigationScreens.Screen.shouldShowBottomBar(currentRoute)) {
                            BottomNavigationBar(navController = navController) // Display the bottom navigation bar
                        }
                    })
            }
        }
    }
}
