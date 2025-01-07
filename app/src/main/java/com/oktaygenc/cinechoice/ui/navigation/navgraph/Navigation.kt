package com.oktaygenc.cinechoice.ui.navigation.navgraph

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
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.ui.presentation.cart.screen.CartScreen
import com.oktaygenc.cinechoice.ui.presentation.detail.screen.DetailScreen
import com.oktaygenc.cinechoice.ui.presentation.explore.screen.ExploreScreen
import com.oktaygenc.cinechoice.ui.presentation.favorite.FavoriteScreen
import com.oktaygenc.cinechoice.ui.presentation.login.screen.LoginScreen
import com.oktaygenc.cinechoice.ui.presentation.movielist.screen.MovieListScreen
import com.oktaygenc.cinechoice.ui.presentation.movielist.viewmodel.MovieListScreenViewModel
import com.oktaygenc.cinechoice.ui.presentation.onboarding.screen.OnboardingScreen
import com.oktaygenc.cinechoice.ui.presentation.profile.screen.ProfileScreen
import com.oktaygenc.cinechoice.ui.presentation.register.screen.RegisterScreen
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
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
        composable("onBoarding") {
            OnboardingScreen { navController.navigate("home") }
        }
        composable("login") {
            LoginScreen(onLoginSuccess = { navController.navigate("home") })
        }
        composable("register") {
            RegisterScreen(onRegisterSuccess = { navController.navigate("onBoarding") })
        }
        composable("explore") {
            ExploreScreen(navController)
        }
        composable("favorite") {
            FavoriteScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController)
        }
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