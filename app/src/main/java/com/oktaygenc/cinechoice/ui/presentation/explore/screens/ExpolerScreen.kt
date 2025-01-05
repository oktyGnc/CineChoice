package com.oktaygenc.cinechoice.ui.presentation.explore.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.oktaygenc.cinechoice.data.model.Movie
import com.oktaygenc.cinechoice.ui.presentation.explore.components.MovieCardForExplore
import com.oktaygenc.cinechoice.ui.presentation.explore.components.SearchBar
import com.oktaygenc.cinechoice.ui.presentation.explore.viewmodel.ExploreViewModel
import com.oktaygenc.cinechoice.ui.presentation.movielist.components.BottomNavigationBar

@Composable
fun ExploreScreen(
    navController: NavHostController
) {
    val viewModel: ExploreViewModel = hiltViewModel()
    val state = viewModel.movies.value
    val searchQuery = viewModel.searchQuery.value

    Scaffold(
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    SearchBar(viewModel = viewModel)
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn {
                        items(state.filter { movie ->
                            movie.name.contains(searchQuery, ignoreCase = true)
                        }) { movie ->
                            MovieCardForExplore(
                                movie = movie,
                                modifier = Modifier.padding(bottom = 8.dp),
                                onNavigateDetail = {
                                    val movieJson = Gson().toJson(it)
                                    navController.navigate("detail/$movieJson")
                                }
                            )
                        }
                    }
                }
            }
        },
        containerColor = Color.White
    )
}
