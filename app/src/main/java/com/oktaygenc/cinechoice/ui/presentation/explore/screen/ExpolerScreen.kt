package com.oktaygenc.cinechoice.ui.presentation.explore.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.oktaygenc.cinechoice.ui.presentation.explore.components.MovieCardForExplore
import com.oktaygenc.cinechoice.ui.presentation.explore.components.SearchBar
import com.oktaygenc.cinechoice.ui.presentation.explore.viewmodel.ExploreViewModel

@Composable
fun ExploreScreen(
    navController: NavHostController,
) {
    val viewModel: ExploreViewModel = hiltViewModel() // Get the ViewModel using Hilt
    val state = viewModel.movies.value // Get the list of movies
    val searchQuery = viewModel.searchQuery.value // Get the current search query

    Scaffold(
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    SearchBar(viewModel = viewModel) // Search bar component
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn {
                        // Filter and display movies based on search query
                        items(state.filter { movie ->
                            movie.name.contains(searchQuery, ignoreCase = true)
                        }) { movie ->
                            MovieCardForExplore(movie = movie,
                                modifier = Modifier.padding(bottom = 8.dp),
                                onNavigateDetail = {
                                    val movieJson =
                                        Gson().toJson(it) // Convert movie object to JSON
                                    navController.navigate("detail/$movieJson") // Navigate to movie detail screen
                                })
                        }
                    }
                }
            }
        }, containerColor = Color.White
    )
}
