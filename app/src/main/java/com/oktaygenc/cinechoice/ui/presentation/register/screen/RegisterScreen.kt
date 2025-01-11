package com.oktaygenc.cinechoice.ui.presentation.register.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.ExperimentalAssetLoader
import com.oktaygenc.cinechoice.R
import com.oktaygenc.cinechoice.ui.presentation.register.viewmodel.RegisterViewModel
import com.oktaygenc.cinechoice.ui.theme.SelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.TextSelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.TopAndBottomBarColor
import com.oktaygenc.cinechoice.ui.theme.TopBarColor
import com.oktaygenc.cinechoice.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Constants for Rive animation state machine
private const val STATE_MACHINE_NAME = "State Machine 1"
private const val DELAY = 500L

@OptIn(ExperimentalAssetLoader::class)
@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit,
    goLogin: () -> Unit,
) {
    // Collect register state from ViewModel
    val registerState by registerViewModel.registerState.collectAsState()

    // Manage input states and focus
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailFocus by remember { mutableStateOf(false) }
    var isPasswordFocus by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var riveView: RiveAnimationView? by remember { mutableStateOf(null) }

    // Animate Rive character based on focus and input
    LaunchedEffect(isPasswordFocus) {
        riveView?.setBooleanState(STATE_MACHINE_NAME, "hands_up", isPasswordFocus)
    }

    // Main screen layout with Rive animation and registration form
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TopAndBottomBarColor)
    ) {
        // Rive animation view
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .scale(1.2f, 1.2f),
            factory = { context ->
                RiveAnimationView(context).apply {
                    setRiveResource(
                        resId = R.raw.login_and_register_animation,
                        stateMachineName = STATE_MACHINE_NAME,
                        alignment = app.rive.runtime.kotlin.core.Alignment.BOTTOM_CENTER
                    )
                    riveView = this
                }
            }
        )

        // Registration form column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SelectedButtonColor)
                .weight(0.5f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Email and password input fields
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isEmailFocus = focusState.isFocused
                    }
            )

            // Register button with state handling
            Button(
                onClick = {
                    scope.launch {
                        // Trigger registration process
                        registerViewModel.register(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            // Registration state handling
            when (registerState) {
                is Resource.Loading -> CircularProgressIndicator()
                is Resource.Error -> Text("Registration failed")
                is Resource.Success -> {
                    LaunchedEffect(Unit) { onRegisterSuccess() }
                }
                is Resource.Empty -> {}
            }
        }
    }
}