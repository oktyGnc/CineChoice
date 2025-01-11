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
import com.oktaygenc.cinechoice.ui.navigation.navgraph.Navigation
import com.oktaygenc.cinechoice.ui.navigation.screens.NavigationScreens
import com.oktaygenc.cinechoice.ui.presentation.movielist.components.BottomNavigationBar
import com.oktaygenc.cinechoice.ui.theme.CineChoiceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // This annotation is used to indicate that the MainActivity will inject dependencies with Hilt.
class MainActivity : ComponentActivity() {

    // onCreate method is the entry point where we set up the UI and handle navigation.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Using Jetpack Compose to set the content view
        setContent {

            // Remember the NavController for managing navigation between screens
            val navController = rememberNavController()

            // Current route, used for conditionally displaying components (like bottom bar)
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route

            // App theme applied here using the 'CineChoiceTheme'
            CineChoiceTheme {

                // Scaffold provides the basic layout structure
                Scaffold(
                    // Content of the screen, including navigation
                    content = {
                        Navigation(
                            modifier = Modifier
                                .fillMaxSize() // Make the navigation take up all available space
                                .padding(it), // Apply any padding (in this case, from the Scaffold)
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
