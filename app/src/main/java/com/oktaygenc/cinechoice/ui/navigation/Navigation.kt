package com.oktaygenc.cinechoice.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.oktaygenc.cinechoice.data.model.Movie
import com.oktaygenc.cinechoice.ui.presentation.cart.screens.CartScreen
import com.oktaygenc.cinechoice.ui.presentation.detail.screens.DetailScreen
import com.oktaygenc.cinechoice.ui.presentation.explore.screens.ExploreScreen
import com.oktaygenc.cinechoice.ui.presentation.favorite.FavoriteScreen
import com.oktaygenc.cinechoice.ui.presentation.movielist.screens.MovieListScreen
import com.oktaygenc.cinechoice.ui.presentation.movielist.viewmodel.MovieListScreenViewModel
import com.oktaygenc.cinechoice.ui.presentation.profile.screens.ProfileScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val viewModel: MovieListScreenViewModel = hiltViewModel()
            val state by viewModel.state
            MovieListScreen(
                state = state,
                onNavigateCart = { navController.navigate("cart") },
                onNavigateDetail = {
                    val movieJson = Gson().toJson(it)
                    navController.navigate("detail/$movieJson")
                },
                onAction = viewModel::onAction
            )
        }
        composable("explore") { ExploreScreen(navController) }


        composable("favorite") { FavoriteScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable(
            "detail/{comingMovie}",
            arguments = listOf(navArgument("comingMovie") { type = NavType.StringType })
        ) {
            val json = it.arguments?.getString("comingMovie")
            val comingMovie = Gson().fromJson(json, Movie::class.java)
            DetailScreen(navController, comingMovie)
        }

        composable("cart") {
            CartScreen(navController)
        }
    }
}
