package com.oktaygenc.cinechoice.presentation.navigation.navgraph

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
import com.oktaygenc.cinechoice.presentation.screens.cart.screen.CartScreen
import com.oktaygenc.cinechoice.presentation.screens.detail.screen.DetailScreen
import com.oktaygenc.cinechoice.presentation.screens.explore.screen.ExploreScreen
import com.oktaygenc.cinechoice.presentation.screens.favorite.screens.FavoriteScreen
import com.oktaygenc.cinechoice.presentation.screens.favorite.viewmodel.FavoriteViewModel
import com.oktaygenc.cinechoice.presentation.screens.login.screen.LoginScreen
import com.oktaygenc.cinechoice.presentation.screens.movielist.screen.MovieListScreen
import com.oktaygenc.cinechoice.presentation.screens.movielist.viewmodel.MovieListScreenViewModel
import com.oktaygenc.cinechoice.presentation.screens.onboarding.screen.OnboardingScreen
import com.oktaygenc.cinechoice.presentation.screens.profile.screen.ProfileScreen
import com.oktaygenc.cinechoice.presentation.screens.register.screen.RegisterScreen
import com.oktaygenc.cinechoice.presentation.screens.splash.SplashScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable("home") {
            // Movie list screen with view model and navigation actions
            val viewModel: MovieListScreenViewModel = hiltViewModel()
            val state by viewModel.state
            MovieListScreen(state = state,
                onNavigateCart = { navController.navigate("cart") },
                onNavigateDetail = {
                    val movieJson = Gson().toJson(it)
                    navController.navigate("detail/$movieJson")
                },
                onAction = viewModel::onAction
            )
        }
        composable("splash") {
            // Splash screen
            SplashScreen(navController)
        }
        composable("onBoarding") {
            // Onboarding screen with navigation to home
            OnboardingScreen { navController.navigate("home") }
        }
        composable("login") {
            // Login screen with navigation actions
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") },
                goRegister = { navController.navigate("register") })
        }
        composable("register") {
            // Register screen with navigation actions
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("onBoarding") },
                goLogin = { navController.navigate("login") })
        }
        composable("explore") {
            // Explore screen
            ExploreScreen(navController)
        }
        composable("favorite") {
            // Favorite screen with view model
            val viewModel: FavoriteViewModel = hiltViewModel()
            FavoriteScreen(
                navController = navController, viewModel = viewModel
            )
        }
        composable("profile") {
            // Profile screen
            ProfileScreen(navController)
        }
        composable(
            "detail/{comingMovie}",
            arguments = listOf(navArgument("comingMovie") { type = NavType.StringType })
        ) {
            // Movie detail screen, receiving the movie as argument
            val json = it.arguments?.getString("comingMovie")
            val comingMovie = Gson().fromJson(json, Movie::class.java)
            DetailScreen(navController, comingMovie)
        }
        composable("cart") {
            // Cart screen
            CartScreen(navController)
        }
    }
}
