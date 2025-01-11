package com.oktaygenc.cinechoice.presentation.screens.profile.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.oktaygenc.cinechoice.presentation.screens.profile.components.ProfileContent
import com.oktaygenc.cinechoice.presentation.screens.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    Scaffold(content = { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            ProfileContent(viewModel = viewModel, onLogoutSuccess = {
                // Navigate to the login screen after successful logout
                navController.navigate("login") {
                    // Remove all previous screens in the navigation stack
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            })
        }
    })
}

