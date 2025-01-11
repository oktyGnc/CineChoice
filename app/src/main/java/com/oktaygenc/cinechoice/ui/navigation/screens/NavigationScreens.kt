package com.oktaygenc.cinechoice.ui.navigation.screens

class NavigationScreens {
    sealed class Screen(val route: String) {
        data object Home : Screen("home") // Home screen route
        data object Explore : Screen("explore") // Explore screen route
        data object Favorite : Screen("favorite") // Favorite screen route
        data object Profile : Screen("profile") // Profile screen route
        data object Cart : Screen("cart") // Cart screen route
        data object Login : Screen("login") // Login screen route
        data object Register : Screen("register") // Register screen route
        data object OnBoarding : Screen("onBoarding") // Onboarding screen route
        data object Detail : Screen("detail/{comingMovie}") // Movie detail screen route
        data object Splash : Screen("splash") // Splash screen route

        companion object {
            // Determines if bottom bar should be shown based on the current route
            fun shouldShowBottomBar(route: String?): Boolean {
                return when (route) {
                    Home.route,
                    Explore.route,
                    Favorite.route,
                    Profile.route,
                    Cart.route -> true // Bottom bar shown for these screens
                    else -> false // Bottom bar hidden for other screens
                }
            }
        }
    }
}
