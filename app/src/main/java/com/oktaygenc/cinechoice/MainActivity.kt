package com.oktaygenc.cinechoice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.oktaygenc.cinechoice.ui.navigation.Navigation
import com.oktaygenc.cinechoice.ui.presentation.movielist.components.BottomNavigationBar
import com.oktaygenc.cinechoice.ui.theme.CineChoiceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
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
                        BottomNavigationBar(navController = navController)
                    }
                )
            }
        }
    }
}
