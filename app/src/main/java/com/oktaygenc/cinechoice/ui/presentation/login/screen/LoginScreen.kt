package com.oktaygenc.cinechoice.ui.presentation.login.screen


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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.ExperimentalAssetLoader
import com.oktaygenc.cinechoice.R
import com.oktaygenc.cinechoice.ui.presentation.login.viewmodel.LoginViewModel
import com.oktaygenc.cinechoice.ui.theme.SelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.TextSelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.TopAndBottomBarColor
import com.oktaygenc.cinechoice.ui.theme.TopBarColor
import com.oktaygenc.cinechoice.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val STATE_MACHINE_NAME = "State Machine 1"
private const val DELAY = 500L

@OptIn(ExperimentalAssetLoader::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    goRegister: () -> Unit
) {
    val loginState by loginViewModel.loginState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailFocus by remember { mutableStateOf(false) }
    var isPasswordFocus by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var riveView: RiveAnimationView? by remember { mutableStateOf(null) }

    LaunchedEffect(isPasswordFocus) {
        riveView?.setBooleanState(STATE_MACHINE_NAME, "hands_up", isPasswordFocus)
    }

    LaunchedEffect(isEmailFocus) {
        riveView?.setBooleanState(STATE_MACHINE_NAME, "Check", isEmailFocus)
    }

    LaunchedEffect(email) {
        riveView?.setNumberState(STATE_MACHINE_NAME, "Look", 2 * email.length.toFloat())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SelectedButtonColor)
    ) {
        AndroidView(modifier = Modifier
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
            })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(TopAndBottomBarColor)
                .weight(0.5f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isEmailFocus = focusState.isFocused
                    })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isPasswordFocus = focusState.isFocused
                    })
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    scope.launch {
                        isEmailFocus = false
                        isPasswordFocus = false
                        delay(DELAY)
                        loginViewModel.login(email, password)
                        if (loginState is Resource.Success) {
                            riveView?.fireState(STATE_MACHINE_NAME, "success")
                        } else {
                            riveView?.fireState(STATE_MACHINE_NAME, "fail")
                        }

                    }
                }, modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = SelectedButtonColor,
                    contentColor = TextSelectedButtonColor
                )
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Don't have an account? Sign up",
                color = SelectedButtonColor,
                modifier = Modifier.clickable {
                    goRegister()
                })
            when (loginState) {
                is Resource.Loading -> CircularProgressIndicator(
                    color = SelectedButtonColor
                )
                is Resource.Error -> Text(
                    text = "E-mail or Password incorrect", color = MaterialTheme.colors.error
                )

                is Resource.Success -> {
                    LaunchedEffect(Unit) { onLoginSuccess() }
                }

                is Resource.Empty -> {}
            }
        }
    }
}