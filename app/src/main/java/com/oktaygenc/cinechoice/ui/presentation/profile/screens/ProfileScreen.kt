package com.oktaygenc.cinechoice.ui.presentation.profile.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.oktaygenc.cinechoice.ui.presentation.movielist.components.BottomNavigationBar
import com.oktaygenc.cinechoice.ui.presentation.profile.components.ProfileContent
import com.oktaygenc.cinechoice.ui.presentation.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }
    val userState by viewModel.userState.collectAsState()

    MaterialTheme(colors = if (isDarkTheme) darkColors() else lightColors()) {
        Scaffold(
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    ProfileContent(
                        isDarkTheme = isDarkTheme,
                        onThemeChange = { isDarkTheme = it },
                        viewModel = viewModel,
                        onLogoutSuccess = {
                            // Login ekranına yönlendir
                            navController.navigate("login") {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }
        )
    }
}
