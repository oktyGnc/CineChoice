package com.oktaygenc.cinechoice.ui.presentation.favorite.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
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

@Composable
fun FavoriteScreen(
    navController: NavHostController,
    viewModel: FavoriteViewModel
) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
        isLoading = false
    }

    val favoriteMovies = viewModel.favoriteMovies.collectAsStateWithLifecycle()

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (favoriteMovies.value.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Henüz favori film eklenmemiş",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Button(
                        onClick = { navController.navigate("home") },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Filmleri Keşfet")
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favoriteMovies.value) { movie ->
                        FavoriteCard(
                            movie = movie,
                            onRemoveClick = { viewModel.removeFavoriteMovie(movie.id) },
                            onMovieClick = {
                                navController.navigate("movieDetail/${movie.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}
