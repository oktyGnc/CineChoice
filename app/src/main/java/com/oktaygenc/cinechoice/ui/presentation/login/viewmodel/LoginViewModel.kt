package com.oktaygenc.cinechoice.ui.presentation.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.utils.Resource
import com.oktaygenc.cinechoice.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository // Repository for authentication
) : ViewModel() {
    companion object {
        private const val TAG = "LoginViewModel" // Log tag
    }

    private val _loginState = MutableStateFlow<Resource<String>>(Resource.Empty) // Track login state
    val loginState: StateFlow<Resource<String>> = _loginState // Public state

    // Login function
    fun login(email: String, password: String) {
        Log.d(TAG, "Login initiated") // Log login initiation
        _loginState.value = Resource.Loading // Set state to loading
        viewModelScope.launch {
            Log.d(TAG, "Calling repository login") // Log repo call
            try {
                val result = authRepository.login(email, password) // Call repo for login
                Log.d(TAG, "Login result: $result") // Log result
                _loginState.value = result // Update state
            } catch (e: Exception) {
                Log.e(TAG, "Login failed", e) // Log error
                _loginState.value = Resource.Error(e.message ?: "An error occurred") // Set error state
            }
        }
    }
}

