package com.oktaygenc.cinechoice.ui.presentation.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.oktaygenc.cinechoice.ui.presentation.movielist.components.BottomNavigationBar

@Composable
fun FavoriteScreen(navController: NavHostController) {
    Scaffold(
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Text("Favorite Screen")
            }
        },
    )
}