package com.oktaygenc.cinechoice.ui.navigation.screens

class NavigationScreens {
    sealed class Screen(val route: String) {
        data object Home : Screen("home")
        data object Explore : Screen("explore")
        data object Favorite : Screen("favorite")
        data object Profile : Screen("profile")
        data object Cart : Screen("cart")
        data object Login : Screen("login")
        data object Register : Screen("register")
        data object OnBoarding : Screen("onBoarding")
        data object Detail : Screen("detail/{comingMovie}")
        data object Splash : Screen("splash")

        companion object {
            fun shouldShowBottomBar(route: String?): Boolean {
                return when (route) {
                    Home.route,
                    Explore.route,
                    Favorite.route,
                    Profile.route,
                    Cart.route -> true
                    else -> false
                }
            }
        }
    }
}