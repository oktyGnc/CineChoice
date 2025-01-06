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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

            CineChoiceTheme {
                Scaffold(
                    content = {
                        Navigation(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            navController = navController
                        )
                    },
                    bottomBar = {
                        if (NavigationScreens.Screen.shouldShowBottomBar(currentRoute)) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                )
            }
        }
    }
}
