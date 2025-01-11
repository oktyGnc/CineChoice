package com.oktaygenc.cinechoice.ui.presentation.favorite.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.oktaygenc.cinechoice.ui.presentation.favorite.components.FavoriteCard
import com.oktaygenc.cinechoice.ui.presentation.favorite.viewmodel.FavoriteViewModel
import com.oktaygenc.cinechoice.ui.theme.SelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.TextSelectedButtonColor
import kotlinx.coroutines.delay

@Composable
fun FavoriteScreen(
    navController: NavHostController, // Navigation controller for screen navigation
    viewModel: FavoriteViewModel // ViewModel to manage favorite movies
) {
    var isLoading by remember { mutableStateOf(true) } // Loading state to show progress indicator

    LaunchedEffect(Unit) {
        delay(2000) // Simulate loading delay
        viewModel.loadFavorites() // Load favorite movies from ViewModel
        isLoading = false // Set loading state to false once data is loaded
    }

    val favoriteMovies = viewModel.favoriteMovies.collectAsStateWithLifecycle() // Observe favorite movies

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (isLoading) { // Show loading indicator while data is loading
                CircularProgressIndicator(
                    color = SelectedButtonColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (favoriteMovies.value.isEmpty()) { // Display message when no favorites are added
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No favorite movie added yet", // Message when there are no favorites
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Button(
                        onClick = { navController.navigate("home") }, // Navigate to home screen
                        modifier = Modifier.padding(top = 8.dp), colors = ButtonDefaults.buttonColors(
                            containerColor = SelectedButtonColor,
                            contentColor = TextSelectedButtonColor
                        )
                    ) {
                        Text("Discover Movies") // Text on the button
                    }
                }
            } else { // Display list of favorite movies if available
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favoriteMovies.value) { movie -> // Iterate over favorite movies
                        FavoriteCard(
                            movie = movie,
                            onRemoveClick = { viewModel.removeFavoriteMovie(movie.id) } // Remove movie from favorites
                        )
                    }
                }
            }
        }
    }
}

